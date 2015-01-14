
package com.example.testroot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends Activity {

    private Button mButtonGetRootPermisson;
    private Button mButtonTrySwitchWifi;
    private Button mButtonTrySwitchData;
    private Button mButtonTryRecorderTel;
    private Button mButtonTryRecorderLocal;
    private Button mButtonTryCamera;
    private Button mButtonTryAccessContacts;
    private Button mButtonTryAccessCalllist;
    private Button mButtonTryAccessSms;
    private Button mButtonTryAccessMms;
    private Button mButtonTrySwitchBt;
    private Button mButtonTrySwitchNfc;
    private Button mButtonTryCall10086;
    private Button mButtonTryGetLocation;
    private Button mButtonTryMMSSMS;
    private Button mButtonTrySendEmail;
    private Button mButtonTryReboot;
    private Process mProcess;

    private SmsMessageReceiver mSmsMsgReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.getExternalFilesDir("louyong");
        
        File testFile =  new File("/sdcard1/test.txt");
        if (!testFile.exists()) {
            try {
                testFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        findView();
        initView();
        
        mSmsMsgReceiver = new SmsMessageReceiver();
        registerReceiver(mSmsMsgReceiver,
                new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }

    private void initView() {
        mButtonGetRootPermisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryGetRootPermisson();
            }
        });

        mButtonTryReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryReboot();
            }
        });

        mButtonTrySwitchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySwitchWifi();
            }
        });

        mButtonTrySwitchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToggleData();
            }
        });

        mButtonTryCall10086.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryCallTelephone();
            }
        });

        mButtonTryMMSSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySendSms();
            }
        });

        mButtonTrySendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySendEmail();
            }
        });

        mButtonTryGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryGetLocation();
            }
        });

        mButtonTryRecorderTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRecorder();
            }
        });

        mButtonTryRecorderLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRecorder();
            }
        });

        mButtonTryCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryCamera();
            }
        });

        mButtonTryAccessContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAccessData(v.getId());
            }
        });
        mButtonTryAccessCalllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAccessData(v.getId());
            }
        });
        mButtonTryAccessSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAccessData(v.getId());
            }
        });
        mButtonTryAccessMms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAccessData(v.getId());
            }
        });
        
        mButtonTrySwitchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySwitchBt();
            }
        });
        
        mButtonTrySwitchNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySwitchNfc();
            }
        });

    }

    protected void trySwitchNfc() {
        NfcManager manager = (NfcManager)getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled()) {
            adapter.enableForegroundDispatch(this, null, null, null);
        }
    }

    private void findView() {
        mButtonGetRootPermisson = (Button) findViewById(R.id.get_root_permisson);
        mButtonTryReboot = (Button) findViewById(R.id.try_reboot);
        mButtonTrySwitchWifi = (Button) findViewById(R.id.try_switch_wifi);
        mButtonTrySwitchData = (Button) findViewById(R.id.try_switch_data);
        mButtonTryCall10086 = (Button) findViewById(R.id.try_call_10086);
        mButtonTryMMSSMS = (Button) findViewById(R.id.try_mms_sms);
        mButtonTrySendEmail = (Button) findViewById(R.id.try_send_email);
        mButtonTryGetLocation = (Button) findViewById(R.id.try_get_location);

        mButtonTryRecorderTel = (Button) findViewById(R.id.try_recoder_tel);
        mButtonTryRecorderLocal = (Button) findViewById(R.id.try_recoder_local);

        mButtonTryCamera = (Button) findViewById(R.id.try_camera);

        mButtonTryAccessContacts = (Button) findViewById(R.id.try_access_contacts);
        mButtonTryAccessCalllist = (Button) findViewById(R.id.try_access_calllist);
        mButtonTryAccessSms = (Button) findViewById(R.id.try_access_sms);
        mButtonTryAccessMms = (Button) findViewById(R.id.try_access_mms);
        mButtonTrySwitchBt = (Button) findViewById(R.id.try_switch_buletooth);
        mButtonTrySwitchNfc = (Button) findViewById(R.id.try_switch_nfc);

    }

    protected void trySwitchBt() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
        // 如果本地蓝牙没有开启，则开启  
        if (!mBluetoothAdapter.isEnabled()) { 
            // 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。  
            mBluetoothAdapter.enable();  
        } else {
            mBluetoothAdapter.disable();//关闭蓝牙  
        }
    } 
 
        
    protected void tryAccessData(int i) {
        ContentResolver resolver = getContentResolver();
        Uri uri;
        switch (i) {
            case R.id.try_access_contacts:
                uri = Phone.CONTENT_URI;
                break;
            case R.id.try_access_calllist:
                uri = CallLog.Calls.CONTENT_URI;
                break;
            case R.id.try_access_sms:
                uri = Uri.parse("content://sms/inbox");
                break;
            case R.id.try_access_mms:
                uri = Uri.parse("content://mms");
                break;
            default:
                uri = Phone.CONTENT_URI;
                break;

        }
        Cursor phoneCursor = resolver.query(uri, null, null, null, null);
    }

    private void tryCamera() {
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void tryRecorder() {
        try {
            MediaRecorder mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/sdcard/test.3gp");
            mediaRecorder.prepare();
            mediaRecorder.start();
            mediaRecorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void tryGetLocation() {
        String serviceString = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(serviceString);
        String provider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);

    }

    protected void trySendEmail() {
        Uri uri = Uri.parse("mailto:xxx@abc.com");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(it);
    }

    protected void trySendSms() {
        // 获取短信管理器
        SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage("test");
        for (String text : divideContents) {
            smsManager.sendTextMessage("10086", null, text, null, null);
        }

    }

    protected void tryCallTelephone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10086"));// ACTION_DIAL
        startActivity(intent);

    }

    protected void trySwitchWifi() {
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wm.getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
            wm.setWifiEnabled(true);
        } else {
            wm.setWifiEnabled(false);
        }

    }

    protected void tryReboot() {
        if (null == mProcess)
            return;
        try {
            DataOutputStream os = new DataOutputStream(mProcess.getOutputStream());
            os.writeBytes("reboot\n");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void tryGetRootPermisson() {
        try {
            mProcess = Runtime.getRuntime().exec("/system/xbin/su");
//            DataInputStream ise = new DataInputStream(mProcess.getErrorStream());
//            InputStreamReader isr = new InputStreamReader(ise);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                Log.e("louyong", line);
//            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    };

    public void tryToggleData() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = connManager.getClass();
        Class<?>[] argClasses = new Class[1];
        argClasses[0] = boolean.class;

        Method method;

        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isEnable = false;
        if (mMobile != null) {
            isEnable = !mMobile.isConnected();
        }

        try {
            method = cmClass.getMethod("setMobileDataEnabled", argClasses);
            method.invoke(connManager, isEnable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private class SmsMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras == null)
                return;

            Object[] pdus = (Object[]) extras.get("pdus");

            for (int i = 0; i < pdus.length; i++) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Log.d("TestRoot", String.format("SMS received from %s, body: %s",
                        message.getOriginatingAddress(), message.getMessageBody()));
                replyMessage(context, message);
            }
        }

        // Log received sms message into an output file

        private void replyMessage(Context context, SmsMessage msg) {
            SmsManager sms = SmsManager.getDefault();
            String message = msg.getMessageBody();
            sms.sendTextMessage(msg.getOriginatingAddress(), null, message, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        this.unregisterReceiver(mSmsMsgReceiver);
        super.onDestroy();
    }
    

}
