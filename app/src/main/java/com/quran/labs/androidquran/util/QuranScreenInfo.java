package com.quran.labs.androidquran.util;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;

import com.quran.data.page.provider.common.PageSizeCalculator;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class QuranScreenInfo {
  private final int height;
  private final int altDimension;
  private final int maxWidth;
  private final int orientation;
  private final Context context;
  private final PageSizeCalculator pageSizeCalculator;

  @Inject
  public QuranScreenInfo(@NonNull Context appContext,
                         @NonNull Display display,
                         @NonNull PageSizeCalculator pageSizeCalculator) {
    final Point point = new Point();
    display.getSize(point);

    height = point.y;
    altDimension = point.x;
    maxWidth = (point.x > point.y) ? point.x : point.y;
    orientation = appContext.getResources().getConfiguration().orientation;

    this.context = appContext;
    this.pageSizeCalculator = pageSizeCalculator;
    Timber.d("initializing with %d and %d", point.y, point.x);

    setOverrideParam(QuranSettings.getInstance(context).getDefaultImagesDirectory());
  }

  public void setOverrideParam(String overrideParam) {
    if (!overrideParam.isEmpty()) {
      pageSizeCalculator.setOverrideParameter(overrideParam);
    }
  }

  public int getHeight() {
    if (orientation == context.getResources().getConfiguration().orientation) {
      return height;
    } else {
      return altDimension;
    }
  }

  public String getWidthParam() {
    return "_" + pageSizeCalculator.getWidthParameter();
  }

  public String getTabletWidthParam() {
    return "_" + pageSizeCalculator.getTabletWidthParameter();
  }

  public boolean isDualPageMode() {
    return maxWidth > 800;
  }
}
