package dgrp.classtrial;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;

import dgrp.classtrial.wizard.ClassTrialWizard;


public class ClassTrialHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        IWizard wizard = new ClassTrialWizard();
        WizardDialog dialog = new WizardDialog(null, wizard);
        dialog.open();

        return null;
    }

}
