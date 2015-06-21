package com.valhala.tarefa.qualifiers;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Annotation utilizada para marcar uma classe como auditavel.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@InterceptorBinding
public @interface Auditavel {

} // fim da annotation Auditavel
