//package com.example.pracitcingrecievingbtc.Factory;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentFactory;
//import com.example.pracitcingrecievingbtc.View.HistoryOfPricesFragment;
//
//import java.security.Provider;
//
//public class BitcoinFragmentFactory extends FragmentFactory {
//
//    Provider<Fragment> providers;
//
//
//    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
//        final Class<?> classKey;
//        try {
//            classKey = Class.forName(className);
//            final Provider<Fragment> provider = providers.get(classKey);
//            if (provider != null) {
//                return provider.get();
//            } catch(ClassNotFoundException e){
//                e.printStackTrace();
//            }
//            return super.instantiate(classLoader, className);
//        }
//    }
//
////    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
////        Class clazz = loadFragmentClass(classLoader, className);
////        Fragment fragment = null;
////        if (clazz == HistoryOfPricesFragment.class) {
////            fragment = f.getParentFragment();
////        }
////        return null;
////    }
//    }
