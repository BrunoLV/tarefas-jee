package com.valhala.tarefa.qualifiers;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation personalizada utilizada para marcar os eventos relacionados com a carga de Equipes.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Qualifier
public @interface CargaEquipe {

} // fim da annotation CargaEquipe