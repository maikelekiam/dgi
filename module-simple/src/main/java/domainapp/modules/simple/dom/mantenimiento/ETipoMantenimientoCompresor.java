package domainapp.modules.simple.dom.mantenimiento;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ETipoMantenimientoCompresor {

    T2000("2000", 2000, new ArrayList<String>(Arrays.asList("Tarea 1", "Tarea 2"))),
    T4000("4000", 4000, new ArrayList<String>(Arrays.asList("tarea 15", "tarea 18"))),
    T6000("6000", 6000, new ArrayList<String>(Arrays.asList("tarea 23", "tarea 26")));

    private final String nombre;
    private final int horas;
    private final List<String> listaTareas;


    private ETipoMantenimientoCompresor(String nombre, int horas, List<String> listaTareas){
        this.nombre=nombre;
        this.horas=horas;
        this.listaTareas=listaTareas;
    }

    public String getNombre(){
        return nombre;
    }

    public int getHoras(){
        return horas;
    }

    public List<String> getListaTareas(){
        return listaTareas;
    }
}
