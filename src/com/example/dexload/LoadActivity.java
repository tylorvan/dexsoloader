package com.example.dexload;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.dynamic.impl.IDynamic;

import dalvik.system.DexClassLoader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoadActivity extends Activity {

	// 动态类加载接口
	private static Object lib;
	private static Class ct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		try {
//			loadDexByDexClassLoader();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Button showtip = (Button) findViewById(R.id.btshowTipe);

		showtip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
//				try {
//					loadDexByDexClassLoader();
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				try {
					loadDexByDexClassLoader();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (lib != null && ct != null) {
					// lib.showTipe();
					// ct.get
									try {
//						loadDexByDexClassLoader();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ct.getDeclaredMethod("showTipe", null)
								.invoke(lib, null);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(), "类加载失败", 1500)
							.show();
				}
			}
		});
	}

	/**
	 * 使用DexClassLoader方式加载类
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	void loadDexByDexClassLoader() throws IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException {

		// dex file path(file is apk or jar or zip格式)
		// String dexPath = Environment.getExternalStorageDirectory().toString()
		// + File.separator + "dynamicImp_temp.jar";
		String dexPath = "/sdcard" + File.separator + "dynamicImp_temp.jar";
		((TextView) findViewById(R.id.tvShow)).setText(dexPath);
		// dex解压释放后的目录
		Log.e("DEX", dexPath);

		File dexOutputDirs = getApplicationContext().getDir("dex", 0);
		Log.e("DEX", "dexOutputDirs:" + dexOutputDirs);

		// 解压目录不能为外存储目录，这里google考虑到安全问题，外部存储会报异常
		// String dexOutputDirs =
		// Environment.getExternalStorageDirectory().toString();
		// 1,dex压缩文件的路径 2,dex解压缩后存放的目录 3,C/C++依赖的本地库文件目录,可以为null,4,上一级的类加载器
		DexClassLoader cl = new DexClassLoader(dexPath,
				dexOutputDirs.getAbsolutePath(), null, getClassLoader());
		// 类的装载实现
		try {
			// 使用DexClassLoader加载类
			Class libProviderClazz = cl
					.loadClass("com.dynamic.impl.DynamicImpl");

			Constructor c = libProviderClazz.getConstructor(Context.class);
			ct = libProviderClazz;
			Object own = c.newInstance(this);

			// Method m = libProviderClazz.getDeclaredMethod("DynamicImpl",
			// Context.class);

			libProviderClazz.getDeclaredMethod("showTipe", null).invoke(own,
					null);
			lib = own;
			// Object obj = libProviderClazz.getMethod("sj", S.class).invoke(
			// owner, "Hello");
			// lib = (IDynamic) libProviderClazz.newInstance();
			// if (lib != null) {
			// lib.init(this);
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}