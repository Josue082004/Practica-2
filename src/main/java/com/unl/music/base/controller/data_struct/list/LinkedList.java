package com.unl.music.base.controller.data_struct.list;

import java.util.stream.StreamSupport;

import com.unl.music.base.controller.Utiles;
import com.unl.music.base.models.Cancion;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.HashMap;
import java.util.stream.Stream;

public class LinkedList<E> {
    private Node<E> head;
    private Node<E> last;
    private Integer length;

    public Integer getLength() {
        return this.length;
    }

    public LinkedList() {
        head = null;
        last = null;
        length = 0;
    }

    public Boolean isEmpty() {
        return head == null || length == 0;
    }

    private Node<E> getNode(Integer pos) {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out range");
        } else if (pos == 0) {
            return head;
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return last;
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getDataFirst() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else {
            return head.getData();
        }
    }

    private E getDataLast() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else {
            return last.getData();
        }
    }

    public E get(Integer pos) {
        return getNode(pos).getData();
    }

    private void addFirst(E data) {
        if (isEmpty()) {
            Node<E> aux = new Node<>(data);
            head = aux;
            last = aux;
        } else {
            Node<E> head_old = head;
            Node<E> aux = new Node<>(data, head_old);
            head = aux;
        }
        length++;
    }

    private void addLast(E data) {
        if (isEmpty()) {
            addFirst(data);
        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            length++;
        }

    }

    public void add(E data, Integer pos) throws Exception {
        if (pos == 0) {
            addFirst(data);
        } else if (length.intValue() == pos.intValue()) {
            addLast(data);
        } else {
            Node<E> search_preview = getNode(pos - 1);
            Node<E> search = getNode(pos);
            Node<E> aux = new Node<>(data, search);
            search_preview.setNext(aux);
            length++;
        }
    }

    public void add(E data) {
        addLast(data);
    }

    public String print() {
        if (isEmpty())
            return "Esta vacia";
        else {
            StringBuilder resp = new StringBuilder();
            Node<E> help = head;
            while (help != null) {
                resp.append(help.getData()).append(" - ");
                help = help.getNext();
            }
            resp.append("\n");
            return resp.toString();
        }
    }

    public void update(E data, Integer pos) {
        getNode(pos).setData(data);
    }

    public void clear() {
        head = null;
        last = null;
        length = 0;
    }

    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if (this.length > 0) {
            clazz = head.getData().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, this.length);
            Node<E> aux = head;
            for (int i = 0; i < length; i++) {
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    public int busquedaBinaria(String attribute, String text) {
    E[] arr = this.toArray();
    Utiles util = new Utiles();
    int low = 0, high = arr.length - 1;
    while (low <= high) {
        int mid = (low + high) / 2;
        try {
            Object valor = arr[mid].getClass().getMethod("get" + util.capitalize(attribute)).invoke(arr[mid]);
            String valorStr = valor.toString().toLowerCase();
            String textoStr = text.toLowerCase();
            int cmp = valorStr.compareTo(textoStr);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            break;
        }
    }
    return -1;
}

    public LinkedList<E> toList(E[] matriz) {
        clear();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);
        }
        return this;
    }

    protected E deleteFirst() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = head.getData();
            Node<E> aux = head.getNext();
            head = aux;
            if (length.intValue() == 1)
                last = null;
            length--;
            return element;
        }
    }

    protected E deleteLast() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = last.getData();
            Node<E> aux = getNode(length - 2);
            if (aux == null) {
                last = null;
                if (length == 2) {
                    last = head;
                } else {
                    head = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            length--;
            return element;
        }
    }

    public E delete(Integer pos) throws Exception {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");

        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out range");
        } else if (pos == 0) {
            return deleteFirst();
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(pos - 1);
            Node<E> actualy = getNode(pos);
            E element = preview.getData();
            Node<E> next = actualy.getNext();
            actualy = null;
            preview.setNext(next);
            length--;
            return element;
        }
    }

    public Stream<E> stream() {
        return StreamSupport.stream(java.util.Spliterators.spliterator(this.toArray(), 0), false);
    }

    // QuickSort

    public void quickSort(String attribute, Integer type) {
        E[] arr = this.toArray();
        quick((Cancion[]) arr, 0, arr.length - 1, type);
        this.toList(arr);
    }

    private void quick(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);
            quick(arr, begin, partitionIndex - 1, type);
            quick(arr, partitionIndex + 1, end, type);
        }
    }

    private int partition(Cancion arr[], int begin, int end, Integer type) {
        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;
        return i + 1;
    }

    // ShellSort
    public LinkedList<E> shellSort(String attribute, Integer orden) throws Exception {
        if (isEmpty())
            return this;

        E[] array = this.toArray();
        Utiles util = new Utiles();
        int n = array.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j;
                for (j = i; j >= gap && !util.compararAtributos(attribute, array[j - gap], temp, orden); j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
        return this.toList(array);
    }

    // Busqueda Lineal
    public LinkedList<E> busquedaLineal(String attribute, String text, Integer type) {
        LinkedList<E> resp = new LinkedList<>();
        if (!isEmpty()) {
            E[] arr = this.toArray();
            Utiles util = new Utiles();
            for (int i = 0; i < arr.length; i++) {
                try {
                    Object valor = arr[i].getClass().getMethod("get" + util.capitalize(attribute)).invoke(arr[i]);
                    if (valor != null) {
                        String valorStr = valor.toString().toLowerCase();
                        String textoStr = text.toLowerCase();
                        switch (type) {
                            case 1:
                                if (valorStr.startsWith(textoStr)) {
                                    resp.add(arr[i]);
                                }
                                break;
                            case 2:
                                if (valor instanceof Number && textoStr.matches("\\d+")) {
                                    if (valorStr.equals(textoStr)) {
                                        resp.add(arr[i]);
                                    }
                                } else {
                                    if (valorStr.contains(textoStr)) {
                                        resp.add(arr[i]);
                                    }
                                }
                                break;
                            default:
                                if (valorStr.contains(textoStr)) {
                                    resp.add(arr[i]);
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resp;
    }

    // Busqueda Lineal Binaria

    // public Integer LinealBinario(String attribute, String text, Integer type) {
    //     Integer half = 0;
    //     E[] array = this.toArray();
    //     if (array.length != 0 && !text.isEmpty()) {
    //         half = array.length / 2;
    //         int aux = 0;
    //         System.out.println(text.trim().toLowerCase().charAt(0) + " *** **** ***" + half + ""
    //                 + array[half].get(attribute).toString().trim().toLowerCase().charAt(0));
    //         if (text.trim().toLowerCase().charAt(0) == array[half].get(attribute).toString().trim().toLowerCase()
    //                 .charAt(0))
    //             aux = 1;
    //         else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase()
    //                 .charAt(0))
    //             aux = -1;
    //         half = half * aux;
    //     }
    //     return half;
    // }

    // public LinkedList<HashMap<String, Object>> busquedaLinealBinaria(String attribute, String text, Integer type)
    //         throws Exception {
    //     LinkedList<HashMap<String, String>> lista = all();
    //     LinkedList<HashMap<String, String>> resp = new LinkedList<>();
    //     if (!lista.isEmpty()) {
    //         lista.quickSort(attribute, Utiles.ASCEDENTE);
    //         HashMap<String, String>[] arr = lista.toArray();
    //         Utiles util = new Utiles();
    //         Integer n = LinealBinario(attribute, text, type);
    //         System.out.println("n: " + n);
    //         switch (type) {
    //             case 1:
    //                 if (n > 0) {
    //                     for (int i = n; i < arr.length; i++) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 } else if (n < 0) {
    //                     n *= -1;
    //                     for (int i = n; i >= 0; i--) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 } else {
    //                     for (int i = 0; i < arr.length; i++) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 }

    //                 break;

    //             case 2:
    //                 if (n > 0) {
    //                     for (int i = n; i < arr.length; i++) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 } else if (n < 0) {
    //                     n *= -1;
    //                     for (int i = n; i >= 0; i--) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 } else {
    //                     for (int i = 0; i < arr.length; i++) {
    //                         if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
    //                             resp.add(arr[i]);
    //                         }
    //                     }
    //                 }
    //                 break;
    //             default:
    //                 System.out.println(attribute + " " + text + " " + n);
    //                 // if(n>0){
    //                 // for(int i = n; i < arr.length; i++){
    //                 // if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
    //                 // resp.add(arr[i]);
    //                 // }
    //                 // }
    //                 // }else if(n<0){
    //                 // n *= -1;
    //                 // for(int i = n; i >= 0; i--){
    //                 // if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
    //                 // resp.add(arr[i]);
    //                 // }
    //                 // }
    //                 // }else{
    //                 // for(int i = 0; i < arr.length; i++){
    //                 // if(arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())){
    //                 // resp.add(arr[i]);
    //                 // }
    //                 // }
    //                 // }
    //                 for (int i = 0; i < arr.length; i++) {
    //                     if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
    //                         resp.add(arr[i]);
    //                     }
    //                 }
    //         }
    //         break;
    //     }
    //     return resp;
    // }

    public LinkedList<HashMap<String, String>> busquedaLinealBinaria(String attribute, String text, Integer type) {
    LinkedList<HashMap<String, String>> resp = new LinkedList<>();
    if (!this.isEmpty()) {
        HashMap<String, String>[] arr = (HashMap<String, String>[]) this.toArray();
        // Ordenar antes de buscar
        // Si tienes un método de ordenamiento en LinkedList, úsalo aquí si es necesario
        int half = 0;
        if (arr.length != 0 && !text.isEmpty()) {
            half = arr.length / 2;
            int aux = 0;
            char c1 = text.trim().toLowerCase().charAt(0);
            char c2 = arr[half].get(attribute).toString().trim().toLowerCase().charAt(0);
            if (c1 == c2)
                aux = 1;
            else if (c1 < c2)
                aux = -1;
            half = half * aux;
        }
        switch (type) {
            case 1: // startsWith
                if (half > 0) {
                    for (int i = half; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                } else if (half < 0) {
                    half *= -1;
                    for (int i = half; i >= 0; i--) {
                        if (arr[i].get(attribute).toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                break;
            case 2: // endsWith
                if (half > 0) {
                    for (int i = half; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                } else if (half < 0) {
                    half *= -1;
                    for (int i = half; i >= 0; i--) {
                        if (arr[i].get(attribute).toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                break;
            default: // contains
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].get(attribute).toLowerCase().contains(text.toLowerCase())) {
                        resp.add(arr[i]);
                    }
                }
                break;
        }
    }
    return resp;
}


    public static void main(String[] args) {
        LinkedList<Double> lista = new LinkedList<>();
        try {
            System.out.println("Hola .....");

            lista.add(56.7);
            lista.add(65.7);
            lista.add(78.7);
            lista.add(89.7);
            lista.add(-1.0, lista.getLength());
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        System.out.println("EL FIN");

    }

}
