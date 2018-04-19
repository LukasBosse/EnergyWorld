package eon.ebs.dialogs;

import com.eon.energyworld.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MessageBox extends Activity implements OnClickListener{

	private Button btn_close;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {

		 super.onCreate(savedInstanceState);
		 requestWindowFeature (Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.messagebox);
		 
		 btn_close = (Button) findViewById(R.id.btn_msg_abbr);
		 btn_close.setOnClickListener(this);
		 
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_msg_abbr) {
			this.onBackPressed();	
		}
	}
	
}
