package com.unl.music.base.controller;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import com.unl.music.base.controller.dao.AdapterDao;

public class Utiles {
    public static Integer ASCEDENTE = 1;
    public static Integer DESCENDENTE = 2;
    public static Integer START;// 1
    public static Integer END;// 2
    public static Integer CONSTIANS;// lo que dios quiera

    public String tranformStringFloatTwoDecimal(float dato) {
        // 67.876
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(dato);

    }

    public Boolean constanceArray(Object[] array, String text) {
        Boolean band = false;
        for (Object a : array) {
            if (a.toString().equals(text)) {
                band = true;
                break;
            }
        }
        return band;
    }

    public boolean compararAtributos(String atributo, Object a, Object b, Integer orden) {
        try {
            Object valA, valB;
            if (atributo == null || atributo.isEmpty()) {
                valA = a;
                valB = b;
            } else {
                valA = a.getClass().getMethod("get" + capitalize(atributo)).invoke(a);
                valB = b.getClass().getMethod("get" + capitalize(atributo)).invoke(b);
            }
            int cmp;
            if (valA instanceof Number && valB instanceof Number) {
                Double da = ((Number) valA).doubleValue();
                Double db = ((Number) valB).doubleValue();
                cmp = da.compareTo(db);
            } else {
                cmp = valA.toString().compareTo(valB.toString());
            }
            if (orden == ASCEDENTE) {
                return cmp < 0;
            } else {
                return cmp > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String capitalize(String str) {
        if (str == null || str.isEmpty())
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private <E> Object getAtributAnidado(E obj, String atributo , AdapterDao dao) throws Exception{
        Object ide = getClazz(obj, "id_"+ atributo);
        if (ide == null) return null;

        String atributoAnido = "nombre";
        Object objAnida = dao.listAll().get(((Number) ide).intValue()- 1);
        return getClazz(objAnida, atributoAnido);
    }

    public Object getClazz(Object data, String atributo) throws Exception {
    String getter = "get" + atributo.substring(0,1).toUpperCase() + atributo.substring(1);
    
   /*   if (data == null ){
    System.out.println("NO HAY DATA");

    }   else{
    System.out.println("DATA PERTENECIENTE A " + data.getClass().getSimpleName()); 
    } */   
        for (Method i : data.getClass().getMethods()) {
            if (i.getName().equals(getter)) {
                //System.out.println("SE ENCONTRO EL GETTER DE : "+ getter + i.invoke(data));
                return i.invoke(data);
            }
        }
        
         throw new NoSuchMethodException("No existe el método " + getter + " en " + data.getClass().getName());
    }
}
