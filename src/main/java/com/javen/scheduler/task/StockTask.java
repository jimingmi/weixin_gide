package com.javen.scheduler.task;

import com.javen.weixin.menu.MenuManager;

public class StockTask implements Runnable{

	@Override
	public void run() {
		MenuManager.main(null);;
	}

}
