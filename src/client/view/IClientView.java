/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.ILockObserver;
import java.awt.event.ActionListener;

/**
 *
 * @author al1as
 */
public interface IClientView {
	void createNumberedTextPane();
	void createMenu();
	void createBottomLabels();
	void setStatus(String status);
	void showForm();
	
	void showLoginDialog();
	String getSavingContent();
	int getStartLockPos();
	int getEndLockPos();
	
	void addOpenListener(ActionListener openListener);
	void addSaveListener(ActionListener saveListener);
	void addRefreshListener(ActionListener refreshListene);
	void addLockListener(ActionListener lockListener);
	void addUnlockListener(ActionListener unlockListener);
	
	void addLockObserver(ILockObserver obs);
}
