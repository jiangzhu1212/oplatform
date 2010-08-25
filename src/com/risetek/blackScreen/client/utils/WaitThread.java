package com.risetek.blackScreen.client.utils;

public class WaitThread extends Thread {

	private boolean flag = true;
	
	public void run(){
		while(flag){
			System.out.print(".");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread(){
		flag = false;
	}
	
}
