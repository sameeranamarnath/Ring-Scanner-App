


package kratos.ringscannerapp;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyCommunicationsActivity extends CommunicationsActivity {

    private String mMessageFromServer = "";

    private TextView mMessageTextView;
    private SeekBar mSpeedSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mMessageTextView = (TextView)findViewById(R.id.serverReplyText);

        mSpeedSeekBar = (SeekBar)findViewById(R.id.seekBar);

        mSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser==true) {

                    for (byte b : String.valueOf(progress).getBytes()) {
                        mBluetoothConnection.write(b);
                    }
                    mBluetoothConnection.write((byte)'.');

                    while (mBluetoothConnection.available() > 0) {

                        char c = (char)mBluetoothConnection.read();

                        if (c == '.') {

                            if (mMessageFromServer.length() > 0) {
                                mMessageTextView.setText(mMessageFromServer);
                                mMessageFromServer = "";
                            }
                        }
                        else {
                            mMessageFromServer += c;
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
