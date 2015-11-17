package dgrp.warewolf;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;

import dgrp.warewolf.wizard.WareWolfWizard;


public class WareWolfHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        IWizard wizard = new WareWolfWizard();
        WizardDialog dialog = new WizardDialog(null, wizard);
        dialog.setTitle("bbbbb");
        dialog.open();

        return null;
    }

}
