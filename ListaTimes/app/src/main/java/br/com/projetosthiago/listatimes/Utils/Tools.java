package br.com.projetosthiago.listatimes.Utils;

import android.content.Context;

import junit.framework.Assert;

/**
 * Created by suigv on 22/06/2016.
 */
public class Tools {
    public static int GetDrawableCodeFoto(Context context, String name, String resource){
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name, resource, context.getPackageName());
    }
}
