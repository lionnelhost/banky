package sn.modeltech.banky.domain;

import java.util.UUID;

public class ParametrageGlobalTestSamples {

    public static ParametrageGlobal getParametrageGlobalSample1() {
        return new ParametrageGlobal().idParamGlobal("idParamGlobal1").codeParam("codeParam1").typeParam("typeParam1").valeur("valeur1");
    }

    public static ParametrageGlobal getParametrageGlobalSample2() {
        return new ParametrageGlobal().idParamGlobal("idParamGlobal2").codeParam("codeParam2").typeParam("typeParam2").valeur("valeur2");
    }

    public static ParametrageGlobal getParametrageGlobalRandomSampleGenerator() {
        return new ParametrageGlobal()
            .idParamGlobal(UUID.randomUUID().toString())
            .codeParam(UUID.randomUUID().toString())
            .typeParam(UUID.randomUUID().toString())
            .valeur(UUID.randomUUID().toString());
    }
}
