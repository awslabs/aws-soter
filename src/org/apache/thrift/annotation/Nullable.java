package org.apache.thrift.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Annotation is not included in the ApacheThrift package from Brazil.
 *
 *  Since we don't care about this, and since the actual implementation is empty
 *  anyway, just replace with an empty annotation.
 */

@Retention(RetentionPolicy.CLASS)
public @interface Nullable {

}
