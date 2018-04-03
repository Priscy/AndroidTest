package com.example.priscila.bluetoothtest;
import android.Manifest;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattDescriptor;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BluetoothController extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothManager bluetoothManager;
    BluetoothGatt mBluetoothGatt;

    Handler handler = new Handler();
    Intent intent;
    LinearLayout layoutBluetooth,layoutSplash;

    Button startScann;
    Button btnConect;
    EditText indexInput;
    TextView devicesTextView;
    TextView stateTextView;
    TextView fallsTv;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int ALERT_HIGH = 2;

    public final UUID HEART_RATE_SERVICE_UUID = convertFromInteger(0x180D);
    public final UUID  HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37);
    public final UUID  HEART_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A39);
    public final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902);
    public final UUID GENERIC_SERVICE = convertFromInteger(0x1800);
    public final UUID DEVICE_NAME= convertFromInteger(0X2A00);
    public final UUID CUSTOM_SERVICE = convertFromInteger(0x0001);
    public final UUID CUSTOM_CHAR_READ = convertFromInteger(0x0002);
    public final UUID CUSTOM_CHAR_WRITE = convertFromInteger(0x0003);

    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";



    int deviceIndex = 0;
    ArrayList<BluetoothDevice> devicesDiscovered = new ArrayList<BluetoothDevice>();
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        layoutBluetooth=(LinearLayout)findViewById(R.id.layoutBluetooth);
        layoutSplash=(LinearLayout)findViewById(R.id.layoutSplash);


        layoutBluetooth.setVisibility(LinearLayout.GONE);
        layoutSplash.setVisibility(LinearLayout.VISIBLE);

        startScann = (Button) findViewById(R.id.scanBtn);
        startScann.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScan(true);
            }
        });

        devicesTextView = (TextView) findViewById(R.id.devicesTextView);
        devicesTextView.setMovementMethod(new ScrollingMovementMethod());


        stateTextView = (TextView) findViewById(R.id.stateTextView);

        btnConect = (Button) findViewById(R.id.btnConectar);
        indexInput = (EditText) findViewById(R.id.indexInput);


        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();


        //Ensures bluetooth is available on the device and if not requests user permission
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        //Checks if location is allowed on the app and if not it requests it grant permission
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] Results){
        if(requestCode == PERMISSION_REQUEST_COARSE_LOCATION)
        {
            stateTextView.append("Location permission granted" + "\n");
        } else {
            stateTextView.append("Permission not granted");
        }
    }

    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d(Constants.TAG,"INSIDE ON SCAN RESULT");

            Log.i(Constants.TAG,result.toString());
            //result.getDevice().getName() != null && result.getDevice().getName().startsWith("Pixky")==true && devicesDiscovered.contains(result.getDevice()) == false

            if (devicesDiscovered.contains(result.getDevice()) == false) {
                changeLayout();
                devicesTextView.append("Index: " + deviceIndex + " Device Name: " + result.getDevice().getName() + " rssi: " + result.getRssi() + "\n");
                devicesDiscovered.add(result.getDevice());
                deviceIndex++;
            }
        }
        public void onScanFailed(int errorCode) {
           Log.d(Constants.TAG, "SCAN FAILED");
        }
    };


    public void changeLayout() {
        Log.d(Constants.TAG,"INSIDE CHANGIND LAYOUT");
        layoutBluetooth.setVisibility(LinearLayout.VISIBLE);
        layoutSplash.setVisibility(LinearLayout.GONE);
    }
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                BluetoothController.this.runOnUiThread(new Runnable() {
                    public void run() {
                        devicesTextView.append("Device Connected" + "\n");
                    }
                });

                mBluetoothGatt.discoverServices();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                BluetoothController.this.runOnUiThread(new Runnable() {
                    public void run() {
                        devicesTextView.append("Device disconected");
                    }
                });
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> gattServices = mBluetoothGatt.getServices();

                for (BluetoothGattService gattService : gattServices) {
                    final String serviceUUID = gattService.getUuid().toString();
                    BluetoothController.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  devicesTextView.append( "\n" +"Servicio: " + serviceUUID +  "\n");
                        }
                    });

                    BluetoothGattCharacteristic characteristic =
                            gatt.getService(HEART_RATE_SERVICE_UUID).getCharacteristic(HEART_RATE_MEASUREMENT_CHAR_UUID);

                    gatt.setCharacteristicNotification(characteristic, true);
                    BluetoothGattDescriptor descriptor =
                            characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID);

                    descriptor.setValue(
                            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);

                }
            } else {
               devicesTextView.append("error on services");
            }
        }


        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
            BluetoothController.this.runOnUiThread(new Runnable() {
                public void run() {

                    devicesTextView.append("On characteristic read"  +  "\n");
                    devicesTextView.append("Characteristic passed: " + characteristic.getUuid().toString());
                }
            });
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            readCharacteristic(characteristic);
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BluetoothGattCharacteristic characteristic =
                    gatt.getService(HEART_RATE_SERVICE_UUID)
                            .getCharacteristic(HEART_RATE_CONTROL_POINT_CHAR_UUID);
            characteristic.setValue(new byte[]{1, 1});
            gatt.writeCharacteristic(characteristic);
        }
    };

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        if (HEART_RATE_MEASUREMENT_CHAR_UUID.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;

            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
            }

            final int alert = characteristic.getIntValue(format,1);

            BluetoothController.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    devicesTextView.append("\n" + "Alert: " + alert + "\n");
                }
            });

            if (alert == 71) {
                Constants.typeEvent="Caida";
                startActivity(new Intent(BluetoothController.this, falls.class));
            } else if (alert == 72){
                //setValueVibration();
                Constants.typeEvent="Emergencia";
                startActivity(new Intent(BluetoothController.this, falls.class));
            } else {
                BluetoothController.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        devicesTextView.append("\n" + "Value: " + alert + "\n");
                    }
                });
            }
        } else  if (CUSTOM_CHAR_READ.equals(characteristic.getUuid())){
            final int value= characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8,1);
            BluetoothController.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    devicesTextView.append("\n" + "CHAR READ: " + value + "\n");
                }
            });

        }

    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public void setValueVibration(){
        BluetoothGattCharacteristic characteristic =
                mBluetoothGatt.getService(CUSTOM_SERVICE)
                        .getCharacteristic(CUSTOM_CHAR_WRITE);
        characteristic.setValue(10,BluetoothGattCharacteristic.FORMAT_UINT8, 0);
        mBluetoothGatt.writeCharacteristic(characteristic);
        broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
    }

    public void startScan(final boolean enable){
        final BluetoothLeScanner btScanner = mBluetoothAdapter.getBluetoothLeScanner();

        if (enable) {
            deviceIndex=0;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btScanner.stopScan(leScanCallback);
                    Log.d(Constants.TAG,"STOP SCAN");
                }

            }, SCAN_PERIOD);

            btScanner.startScan(leScanCallback);
            Log.d(Constants.TAG,"START SCAN");
        } else {
            //devicesTextView.append("Scanning stopped" + "\n");
            btScanner.stopScan(leScanCallback);
        }
    }

    public void connectToDevice(View view){
        devicesTextView.append("Trying to connect to device with index: " + indexInput.getText() + "\n");
        int deviceIndex = Integer.parseInt(indexInput.getText().toString());
        mBluetoothGatt = devicesDiscovered.get(deviceIndex).connectGatt(this, true, mGattCallback);


    }

    public void next(View view){
        Log.d(Constants.TAG,"Btn next clicked");
        startActivity(new Intent(BluetoothController.this, pacienteSignup.class));
        //startActivity(new Intent(BluetoothController.this, falls.class));


    }


    public UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
}
