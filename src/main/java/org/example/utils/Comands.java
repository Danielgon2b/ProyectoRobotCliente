package org.example.utils;

public class Comands {

    public static final String MOVER_POSICION_ANTERIOR="DP";
    /*Necesita CRD x y z, <DW x,y,z>*/
    public static final String DESPLAZAR_MANO_CRD="DW";
    /*Necesita el numero de la posicion <HE x>*/
    public static final String GUARDAR_POS_ACTUAL="HE";
    public static final String ESTABLECER_CRD_ACTUAL_CARTESIANAS="HO";
    public static final String MOVER_SIGUIENTE_POS="IP";
    /*Necesita 2 posicines y si va con la pinza abierta o cerrada <MA pos1,pos2,<O,C>>*/
    public static final String MOVER_2_POS="MA";
    /*Necesita 2 posiciones o mas <MC pos1,pos2>*/
    public static final String MOVER_ENTRE_POS_CONTINUO="MC";
    /*Necesita los grados por cada articulacion <MJ <cintura>,<hombre>,<codo>,<pitch>,<roll>>*/
    public static final String MOVER_POR_ARTICULACIONES="MJ";
    /*Necesita la posiscion a la que moverse <MO pos>*/
    public static final String MOVER_A_POS="MO";
    /*Necesita crd x,y,z y angulo pitch y angulo roll <MP <x>,<y>,<z>,<angulo pitch>,<angulo roll>>*/
    public static final String MOVER_MANO_CRD="MP";
    /*Necesita posicion incial, final e intermedias tambien se puede poner si va con la pinza abierta o cerrada <MS <pos>,<posIntermedia>,<O,C>>*/
    public static final String MOVER_DESDE_POS_A_POS_CON_INTER="MS";
    /*Necesita posicion incial y distancia <MT pos,distancia,<c,o>>*/
    public static final String MOVER_DESDE_POS_A_DISTANCIA="MT";
    public static final String ORIGEN_MECANICO="NT";
    public static final String MOVER_DEVUELTA_A_LAS_CRD_DE_HO="OG";
    //TODO
    public static final String ABRIR_PINZA="C";

}
