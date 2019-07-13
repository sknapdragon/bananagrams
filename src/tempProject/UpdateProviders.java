package tempProject;

import lejos.utility.Delay;

public class UpdateProviders extends Thread{

	private RelevantProviders prov;
	protected boolean on = true;
	
	
	public UpdateProviders(RelevantProviders prov) {
		super();
		this.prov = prov;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (on) {
			Delay.msDelay(15);
			prov.updateCompass();
		}
	}
	
	public void stopOp(){
		on = false;
	}
}
