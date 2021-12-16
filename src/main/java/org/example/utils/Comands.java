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
    /*Necesita los grados por cada articulacion <MJ <cintura>,<hombro>,<codo>,<pitch>,<roll>>*/
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
    /*Necesita 3 parametros <PA <numPaleta>,<numCol>,<numFila>>*/
    public static final String DEFINIR_MATRIZ="PA";
    /*Necesita minimo 2 argumentos <PC pos1,pos2,pos3?>*/
    public static final String BORRAR_POS="PC";
    /*Necesita 6 args <PD pos,x,y,z,pitch,roll>*/
    public static final String CREAR_POS_EN_CRD="PD";
    /*Necesita 2 pos <PL posASobrescribir,posACopiar>*/
    public static final String SOBREESCRIBIR_POS_TO_POS="PL";
    /*Necesita la paleta <PT <paleta>>*/
    public static final String CALCULAR_CRD_DE_POS_PALETA="PT";
    /*Necesita las 2 pos para igualar <PX pos1,pos2>*/
    public static final String IGUAL_POS="PX";
    /*Necesita las 2 pos <SF <posParaSumar>,<posSumarEIgualar>>*/
    public static final String SUMAR_POS_TO_POS="SF";
    /*Necesita vel y aceleracion H:Alta L:Baja <SP <0a9>,<HoL>>*/
    public static final String DEFINIR_VELOCIDAD="SP";
    /*Necesita el tiempo <TI <0a32707/10>>*/
    public static final String WAIT="TI";
    /*Necesita la longitud <TL longitud>*/
    public static final String VARIAR_LONGITUD_MANO="TL";
    /*Necista el numero <CP <1a99>>*/
    public static final String CREAR_CONTADOR="CP";
    /*Necesita el bit <DA bit>*/
    public static final String DESHABILITA_BIT_ENTRADA="DA";
    /*Necesita el contador <DC contador>*/
    public static final String REDUCIR_1_CONTADOR="DC";
    /*Necesita las 2 lineas <DL linea1,linea2>*/
    public static final String BORRAR_LINEA_HASTA_LINEA="DL";
    /*Necesita +o-,bit y linea <EA <+o->,<bit>,<linea>>*/
    public static final String CREAR_INTERRUPCION="EA";
    public static final String FINALIZA_PROGRAMA="ED";
    /*Necesita valor del contador y la linea <EQ valor,linea>*/
    public static final String IR_LINEA_EJECUTAR_SI_CONTADOR="EQ";
    /*Necesita linea <GS linea>*/
    public static final String INICIAR_SUBRUTINA="GS";
    /*Necesita linea <GT linea>*/
    public static final String SALTAR_A_LINEA="GT";
    /*Necesita el contador <IC contador>*/
    public static final String SUMAR_1_CONTADOR="IC";
    /*Necesita el valor y linea <LG <-32708a32708>,linea>*/
    public static final String SALTAR_LINEA_EJECUTAR_SI_CONTADOR="LG";
    /*Necesita valor y linea <NE valor,linea>*/
    public static final String SALTAR_LINEA_SI_NO_VALOR="NE";
    public static final String BORRAR_PROGRAMA_Y_POS="NW";
    public static final String FIN_CICLO="NX";
    /*Necesita el numero de repeticiones <RC <1a32767>>*/
    public static final String NUMERO_REPETICIONES="RC";
    public static final String CERRAR_PINZA="GC";
    /*Necesita si abierta o cerrada <GF <0o1>>*/
    public static final String MANTENER_PINZA="GF";
    public static final String ABRIR_PINZA="GO";
    /*Necesita 3 args <GP <fuerzaIncial>,<fuerzaRetencion>,<tiempoAplicacionFuerzaIncial>>*/
    public static final String PONER_FUERZA_PINZA="GP";
    public static final String CAPTURA_DATOS_PUERTO="ID";
    public static final String CAPTURA_DATOS_SYNC="IN";
    /*Necesita si activo:+ o desactiva:- y bit <OB <+o->,bit>*/
    public static final String ACTIVAR_DESACTIVAR_BIT="OB";
    /*Necesita el dato <OD dato(1oBits)>*/
    public static final String FIJAR_DATO_BIT="OD";
    /*Necesita el dato <OT dato(1oBits)>*/
    public static final String GENERAR_DATOS_SYNC="OT";
    /*Necesita el estado +o- en el bit y la linea <TB <+o->,<bit>,<linea>>*/
    public static final String VERIFICAR_BIT_POR_ESTADO_SALTAR_LINEA="TB";
    /*Necesita el contador <CR contador(1a99)>*/
    public static final String LEER_VALOR_CONTADOR="CR";
    public static final String LEER_REGISTRO_INTERNO="DR";
    public static final String LECTURA_TIPO_ERROR="ER";
    /*Necesita la linea <LR linea>*/
    public static final String LEER_LINEA="LR";
    /*Necesita la pos <PR pos>*/
    public static final String LEER_POS="PR";
    public static final String LEER_POS_ACTUAL="WH";
    public static final String TRAER_EL_PROGRAMA_DEL_ROBOT="RS";
    public static final String ENVIAR_PROGRAMA_ROBOT="WR";
}
