package com.seggr.element;

import java.util.*;

import org.apache.log4j.Logger;

import com.audium.server.voiceElement.VoiceElementBase;
import com.audium.server.voiceElement.ElementInterface;
import com.audium.server.voiceElement.Setting;
import com.audium.server.voiceElement.ExitState;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.session.VoiceElementData;
import com.audium.server.xml.VoiceElementConfig;

import com.audium.core.vfc.*;
import com.audium.core.vfc.audio.*;
import com.audium.core.vfc.form.*;
import com.audium.core.vfc.util.*;

public class MyCustomDynamic_Element extends VoiceElementBase implements ElementInterface {
private static Logger logger = Logger.getLogger(MyCustomDynamic_Element.class.getName());

public String getElementName() {
        return "MyCustomDynamic_Element";
}
public String getDescription() {
        return "Does cool dynamic media stuff.";
}
public String getDisplayFolderName() {
        return "MyCompanyName";
}
public String[] getAudioGroupDisplayOrder() {
        return null;
}
public HashMap getAudioGroups() throws ElementException {
        return null;
}
public Setting[] getSettings() throws ElementException {
        Setting myStringData = new Setting("myStringData", "myStringData", "myStringData", true, true, true, Setting.STRING);
        Setting myExample = new Setting("myExample", "myExample", "myExample", true, true, true, Setting.STRING);
        Setting[] cfg_settings = new Setting[] {myStringData, myExample};
        return cfg_settings;
}

public ExitState[] getExitStates() throws ElementException {
        ExitState done = new ExitState("Done","Done","Command Completed.");
        ExitState error = new ExitState("Error","Error","Failed to Complete Command.");
        ExitState[] exitStateArray = new ExitState[] {done, error};
        return exitStateArray;
}

protected String addXmlBody(VMain vxml, Hashtable reParameters, VoiceElementData data) throws VException, ElementException {
        VPreference pref = data.getPreference();
        VForm prompt_form = VForm.getNew(pref, "start");

        VoiceElementConfig cfg = data.getVoiceElementConfig();
        String prompt = cfg.getSettingValue("myStringData", data);

        VBlock prompt_block = VBlock.getNew(pref);
        prompt_form.add(prompt_block);
        VAudio media_item = VAudio.getNew(pref, prompt, VAudio.TTS_ONLY);
        logger.debug("TTS:"+ prompt);
        prompt_block.add(media_item);

        VAction promt_submit = VAction.getNew(pref);
        promt_submit.add(getSubmitVAction(null, pref));
        prompt_block.add(promt_submit);

        vxml.add(prompt_form);
        String exit_state = "Done";
        return exit_state;
}
}
