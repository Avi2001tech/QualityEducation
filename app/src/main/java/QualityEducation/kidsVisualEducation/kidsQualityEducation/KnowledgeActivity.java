package QualityEducation.kidsVisualEducation.kidsQualityEducation;



import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class KnowledgeActivity extends Activity implements OnClickListener,OnTouchListener {
	private String type = "";

	ImageView nextBtn = null;
	ImageView playBtn = null;
	ImageView prevBtn = null;
	ImageView itemImage = null;
	private int currentPosition = 0;
	private int totalItem = 0;
	ResourcePool resourcePool = new ResourcePool();
	private MediaPlayer mediaPlayer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.taw_activity_month);
		

		
		Bundle bundle = getIntent().getExtras();
		type = bundle.getString("type");

		nextBtn = (ImageView) findViewById(R.id.nextId);
		playBtn = (ImageView) findViewById(R.id.playId);
		prevBtn = (ImageView) findViewById(R.id.prevId);
		nextBtn.setOnTouchListener(this);
		playBtn.setOnTouchListener(this);
		prevBtn.setOnTouchListener(this);
		
		itemImage = (ImageView) findViewById(R.id.knowledgeItemId);

		nextBtn.setOnClickListener(this);
		playBtn.setOnClickListener(this);
		prevBtn.setOnClickListener(this);
		itemImage.setOnClickListener(this);

		if(type.equals(ResourcePool.month)){
			totalItem = resourcePool.monthsArray.length;
			itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
		} else {
			totalItem = resourcePool.weeks.length;
			itemImage.setImageResource(resourcePool.weeks[currentPosition]);
		}
		updatePreviousButton();
		playSound();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tawmain, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextId:
			gotoNext();
			break;
		case R.id.prevId:
			gotoPrevious();
			break;
		case R.id.playId:
			playSound();
			break;

		default:
			break;
		}
	}

	private void gotoNext(){
		currentPosition++;
		updateNextButton();
		updatePreviousButton();
		if(currentPosition >= 0 && currentPosition < totalItem)
		{
			if(type.equals(ResourcePool.month)){
				totalItem = resourcePool.monthsArray.length;
				itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
			} else {
				totalItem = resourcePool.weeks.length;
				itemImage.setImageResource(resourcePool.weeks[currentPosition]);
			}
			playSound();
		}
	}

	private void gotoPrevious() {
		currentPosition--;
		updateNextButton();
		updatePreviousButton();
		if(currentPosition >= 0 && currentPosition < totalItem)
		{
			if(type.equals(ResourcePool.month)){
				totalItem = resourcePool.monthsArray.length;
				itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
			} else {
				totalItem = resourcePool.weeks.length;
				itemImage.setImageResource(resourcePool.weeks[currentPosition]);
			}
			playSound();
		}
	}
	
	private void updateNextButton(){
		if(currentPosition == totalItem - 1){
			nextBtn.setAlpha(0.5f);
			nextBtn.setClickable(false);
		}
		else{
			nextBtn.setAlpha(1f);
			nextBtn.setClickable(true);
		}
	}
	
	private void updatePreviousButton(){
		if(currentPosition == 0){
			prevBtn.setAlpha(0.5f);
			prevBtn.setClickable(false);
		}
		else{
			prevBtn.setAlpha(1f);
			prevBtn.setClickable(true);
		}
	}

	private void playSound() {
		if(type.equals(ResourcePool.month))
		{
			mediaPlayer = MediaPlayer.create(KnowledgeActivity.this, resourcePool.monthsSound[currentPosition]);
		}
		else{
			mediaPlayer = MediaPlayer.create(KnowledgeActivity.this, resourcePool.weekSound[currentPosition]);
		}
		mediaPlayer.start();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			v.setAlpha(0.5f);
			break;
		case MotionEvent.ACTION_UP:
			v.setAlpha(1.0f);
			break;

		default:
			break;
		}
		return false;
	}

}
