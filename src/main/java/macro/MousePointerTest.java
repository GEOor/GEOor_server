package macro;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MousePointerTest {
	
	public void test(){
		Timer t = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				PointerInfo pt = MouseInfo.getPointerInfo();
				
				System.out.println(pt.getLocation()); // x,y
			}
		};
		t.schedule(timerTask, 0, 3000);
	}
	
	
	
}
