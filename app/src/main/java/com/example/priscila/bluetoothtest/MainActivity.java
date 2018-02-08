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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothManager bluetoothManager;
    BluetoothGatt mBluetoothGatt;
    Handler handler = new Handler();

    Button startScann;
    Button btnConect;
    EditText indexInput;
    TextView devicesTextView;
    TextView stateTextView;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    public final UUID HEART_RATE_SERVICE_UUID = convertFromInteger(0x180D);
    public final UUID  HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37);
    public final UUID  HEART_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A39);
    public final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902);

    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";



    int deviceIndex = 0;
    ArrayList<BluetoothDevice> devicesDiscovered = new ArrayList<BluetoothDevice>();
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if (devicesDiscovered.contains(result.getDevice()) == false) {
                devicesTextView.append("Index: " + deviceIndex + " Device Name: " + result.getDevice().getName() + " rssi: " + result.getRssi() + "\n");
                devicesDiscovered.add(result.getDevice());
                deviceIndex++;

                // auto scroll for text view
                final int scrollAmount = devicesTextView.getLayout().getLineTop(devicesTextView.getLineCount()) - devicesTextView.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                if (scrollAmount > 0) {
                    devicesTextView.scrollTo(0, scrollAmount);
                }
            }
        }
        public void onScanFailed(int errorCode) {
           stateTextView.append("Scan failed"  + "\n");
        }
    };

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        devicesTextView.append("Device Connected" + "\n");
                    }
                });
                mBluetoothGatt.discoverServices();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                MainActivity.this.runOnUiThread(new Runnable() {
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
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            devicesTextView.append( "\n" +"Servicio: " + serviceUUID +  "\n");
                        }
                    });

                    BluetoothGattCharacteristic characteristic =
                            gatt.getService(HEART_RATE_SERVICE_UUID)
                                    .getCharacteristic(HEART_RATE_MEASUREMENT_CHAR_UUID);
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
            MainActivity.this.runOnUiThread(new Runnable() {
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
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                //Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                //Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
           // Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    devicesTextView.append( "\n" +"Heart Rate: " + heartRate +  "\n");
                }
            });

        } else {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    devicesTextView.append( "\n" +"Not a heart rate profile"+  "\n");
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
    public void startScan(final boolean enable){
        final BluetoothLeScanner btScanner = mBluetoothAdapter.getBluetoothLeScanner();

        if (enable) {
            devicesDiscovered.clear();
            devicesTextView.setText("");
            deviceIndex=0;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btScanner.stopScan(leScanCallback);
                    devicesTextView.append("Scanning stopped" + "\n");
                }

            }, SCAN_PERIOD);

            btScanner.startScan(leScanCallback);
        } else {
            devicesTextView.append("Scanning stopped" + "\n");
            btScanner.stopScan(leScanCallback);
        }
    }

    public void connectToDevice(View view){
        devicesTextView.append("Trying to connect to device with index: " + indexInput.getText() + "\n");
        int deviceIndex = Integer.parseInt(indexInput.getText().toString());
        mBluetoothGatt = devicesDiscovered.get(deviceIndex).connectGatt(this, true, mGattCallback);


    }

    public UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
}
