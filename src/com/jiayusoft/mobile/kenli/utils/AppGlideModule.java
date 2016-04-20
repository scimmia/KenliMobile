package com.jiayusoft.mobile.kenli.utils;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Hi on 2015-7-29.
 */
public class AppGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        DebugLog.e("applyOptions");
        glideBuilder.setMemoryCache(new LruResourceCache(512000));
        glideBuilder.setBitmapPool(new LruBitmapPool(512000));
        glideBuilder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1048576 * 20));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        DebugLog.e("registerComponents");
        glide.setMemoryCategory(MemoryCategory.LOW);
    }
}
