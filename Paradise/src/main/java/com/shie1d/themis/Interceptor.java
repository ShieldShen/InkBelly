package com.shie1d.themis;

import android.os.Bundle;

/**
 * 工作流插补器
 */

public abstract class Interceptor<P> {

    abstract void intercept(P product, Bundle bundle);
}
