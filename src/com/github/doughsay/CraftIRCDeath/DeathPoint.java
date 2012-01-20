package com.github.doughsay.CraftIRCDeath;

import java.util.List;

import com.ensifera.animosity.craftirc.EndPoint;
import com.ensifera.animosity.craftirc.RelayedMessage;
import com.ensifera.animosity.craftirc.SecuredEndPoint.Security;

public class DeathPoint implements EndPoint {

	DeathPoint() { }

	public Type getType() {
        return EndPoint.Type.MINECRAFT;
    }

	public Security getSecurity() {
		return Security.UNSECURED;
	}

    public void messageIn(RelayedMessage msg) {
    	return;
    }

    public boolean userMessageIn(String username, RelayedMessage msg) {
        return false;
    }

    public boolean adminMessageIn(RelayedMessage msg) {
        return false;
    }

    public List<String> listUsers() {
        return null;
    }

    public List<String> listDisplayUsers() {
        return null;
    }
}
