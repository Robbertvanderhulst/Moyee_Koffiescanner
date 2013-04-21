/**
* @title Moyee Barcodescanner
* @author Robbert van der Hulst
* @param S1063848
* @since 04-04-2013
* @version 1.0
*/

package com.simonmacdonald.scan;

import android.app.Activity;
import android.os.Bundle;
import org.apache.cordova.*;

public class BarcodeScanner extends DroidGap
{
	
	/**
	* Roept de HTML pagina van de barcodescanner op.
	*/
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
    }
}

