/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package SO;

import Tablas.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author braul
 */
public class ProyectoFinalSO {
    static ArrayList<AreaLibre> TAL;
    static ArrayList<Proceso> TP;
    
    static Queue<Proceso> procesos;
    static int paso =0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        procesos = new ArrayDeque();
        procesos.add(new Proceso("A", 8, 1, 7));
        procesos.add(new Proceso("B",14, 2, 7));
        procesos.add(new Proceso("C",18, 3, 4));
        procesos.add(new Proceso("D", 6, 4, 6));
        procesos.add(new Proceso("E",14, 5, 5));
        
        //Tabla de Areas Libres
        TAL = new ArrayList();
        AreaLibre.setTAL(TAL);
        AreaLibre.insertar(new AreaLibre(10, 54));

        //Tabla de Particiones
        TP = new ArrayList();
        Particion.setTP(TP);
        System.out.println("Paso 0:");
        System.out.println("-Area Libre");
        AreaLibre.Imprimir();
        System.out.println("-Particion-");
        Particion.Imprimir();
        do{
           Paso();
        }while(!TP.isEmpty());
        
    }
    
    public static void Paso(){
        paso++;
        System.out.println("Paso "+paso+":");
        //Insertar Proceso
        if(!procesos.isEmpty() && procesos.peek().getTiempoLlegada() == paso){
            Proceso actual = procesos.remove();
            int i=0;
            AreaLibre seleccion = TAL.get(i);
            try{
            while(actual.getTamaño()> seleccion.getTamaño() ){
                i++;
                TAL.get(i);
            }
            actual.setLocalidad(seleccion.getLocalidad());
            int localidad = seleccion.getLocalidad() + actual.getTamaño();
            int tamaño = seleccion.getTamaño() - actual.getTamaño();
            
            seleccion.setLocalidad(localidad);
            seleccion.setTamaño(tamaño);
            TP.add(actual);
            }catch(IndexOutOfBoundsException e){
                actual.setTiempoLlegada(paso +1);
                procesos.add(actual);
            }
        }
        //Verificar en cada paso si un proceso ya termino
        ArrayList<Proceso> IteratorTP = (ArrayList<Proceso>) TP.clone();
        for(Proceso p: IteratorTP){
            p.Procesar();
        }

        System.out.println("-Area Libre");
        AreaLibre.Imprimir();
        System.out.println("-Particion-");
        Particion.Imprimir();
        
        //Mover a otra parte para tener acceso fuera del paso
        ArrayList<Ram> tablaRAM = new ArrayList();
        Ram.setTRAM(tablaRAM);
        tablaRAM.add(new Ram(0, 10, "SO"));
        for(Proceso p: TP){
            tablaRAM.add(p);
        }
        for(AreaLibre p: TAL){
            tablaRAM.add(p);
        }
        System.out.println("-RAM-");
        Ram.imprimir();
    }
    
}
