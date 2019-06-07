package type1;

public class updateSensors extends Thread {
	RelevantProviders rp;
	private boolean on= true;

	
	public updateSensors(RelevantProviders prov) {
		rp = prov;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(on)
		rp.updateAllProviders();
	}
	public void stopOP() {
		on = false;
		
	}
}
