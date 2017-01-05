package com.seaway.android.common.observable;

import java.util.Observable;

public class OnBackPressedObservable extends Observable {
	public void onBackPressed() {
		setChanged();
		notifyObservers(this);
	}
}
