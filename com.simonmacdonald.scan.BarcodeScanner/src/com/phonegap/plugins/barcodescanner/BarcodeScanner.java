/**
* @title Moyee Barcodescanner
* @author Robbert van der Hulst
* @param S1063848
* @since 04-04-2013
* @version 1.0
*/

/**
 * Naam van de package.
 */

package com.phonegap.plugins.barcodescanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;

/**
 * De volgende klasse roept de ZXing barcodelezer op en geeft hierna het resultaat.
 */

public class BarcodeScanner extends Plugin {
    private static final String SCAN = "scan";
    private static final String ENCODE = "encode";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String DATA = "data";
    private static final String TYPE = "type";
    private static final String SCAN_INTENT = "com.phonegap.plugins.barcodescanner.SCAN";
    private static final String ENCODE_DATA = "ENCODE_DATA";
    private static final String ENCODE_TYPE = "ENCODE_TYPE";
    private static final String ENCODE_INTENT = "com.phonegap.plugins.barcodescanner.ENCODE";
    private static final String TEXT_TYPE = "TEXT_TYPE";
    private static final String EMAIL_TYPE = "EMAIL_TYPE";
    private static final String PHONE_TYPE = "PHONE_TYPE";
    private static final String SMS_TYPE = "SMS_TYPE";
    public static final int REQUEST_CODE = 0x0ba7c0de;
    public String callback;

    /**
     * De constructor van de applicatie.
     */
    public BarcodeScanner() {
    }

    /**
     * Voert de scanner uit en stuurt de plugin resultaten terug. 
     *
     * @param action        De actie wordt hiermee uitgevoerd.
     * @param args          JSONArray argumenten voor de plugin.
     * @param callbackId    Dit id wordt terug gestuurd bij het gebruiken van javascript.
     * @return              Het plugin resultaat wordt terug gestuurd met een status en een boodschap.
     */
    
    public PluginResult execute(String action, JSONArray args, String callbackId) {
        this.callback = callbackId;

        if (action.equals(ENCODE)) {
            JSONObject obj = args.optJSONObject(0);
            if (obj != null) {
                String type = obj.optString(TYPE);
                String data = obj.optString(DATA);

                /**
                 * 
                 * Als het type 0 is dan wordt het omgezet naar tekst. 
                 * 
                 */
                
                if (type == null) {
                    type = TEXT_TYPE;
                }
                
                /**
                 * 
                 * Als de data 0 is dan wordt er een foutmelding weergegeven.
                 * 
                 */

                if (data == null) {
                    return new PluginResult(PluginResult.Status.ERROR, "Er zijn geen gegevens opgegeven om te coderen");
                }
                
                /**
                 * 
                 * Als de data 0 is dan wordt er een foutmelding weergegeven.
                 * 
                 */
                
                encode(type, data);
            } else {
                return new PluginResult(PluginResult.Status.ERROR, "Er zijn geen gegevens opgegeven om te coderen");
            }
        }
        else if (action.equals(SCAN)) {
            scan();
        } else {
            return new PluginResult(PluginResult.Status.INVALID_ACTION);
        }
        PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
        r.setKeepCallback(true);
        return r;
    }

    /**
     * Start een activiteit om te scannen en te coderen van een barcode.
     */
    
    public void scan() {
        Intent intentScan = new Intent(SCAN_INTENT);
        intentScan.addCategory(Intent.CATEGORY_DEFAULT);
        this.cordova.startActivityForResult((Plugin) this, intentScan, REQUEST_CODE);
    }

    /**
     * Roept de barcode scanner op
     *
     * @param requestCode       Roept de code op die gescant is met de scanner
     * @param resultCode       	Laat het resultaat zien van de code.
     * @param intent            De gegevens worden teruggestuurd naar de gebruiker. 
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(TEXT, intent.getStringExtra("SCAN_RESULT"));
                    obj.put(FORMAT, intent.getStringExtra("SCAN_RESULT_FORMAT"));
                    obj.put(CANCELLED, false);
                } catch(JSONException e) {
                    
                }
                this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
            } if (resultCode == Activity.RESULT_CANCELED) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(TEXT, "");
                    obj.put(FORMAT, "");
                    obj.put(CANCELLED, true);
                } catch(JSONException e) {
                   
                }
                this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
            } else {
                this.error(new PluginResult(PluginResult.Status.ERROR), this.callback);
            }
        }
    }

    /**
     * Kan een barcode encoderen
     * @param data  
     * @param data2
     */
    public void encode(String type, String data) {
        Intent intentEncode = new Intent(ENCODE_INTENT);
        intentEncode.putExtra(ENCODE_TYPE, type);
        intentEncode.putExtra(ENCODE_DATA, data);

        this.cordova.getActivity().startActivity(intentEncode);
    }
}