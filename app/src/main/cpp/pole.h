/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_example_feodal_Pole */

#ifndef _Included_com_example_feodal_Pole
#define _Included_com_example_feodal_Pole

jint quantityPoints_ = 25;
jint minX_;
jint minY_;
jint maxX_ = 0;
jint maxY_ = 0;
jint** points_;
jint** pointsFigures_;

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_example_feodal_Pole_create
        (JNIEnv *, jobject, jint);

JNIEXPORT void JNICALL Java_com_example_feodal_Pole_destroy
        (JNIEnv *, jobject);
/*
 * Class:     com_example_feodal_Pole
 * Method:    getMinX
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getMinX
        (JNIEnv *, jobject);

/*
 * Class:     com_example_feodal_Pole
 * Method:    getMinY
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getMinY
        (JNIEnv *, jobject);

/*
 * Class:     com_example_feodal_Pole
 * Method:    getMaxX
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getMaxX
        (JNIEnv *, jobject);

/*
 * Class:     com_example_feodal_Pole
 * Method:    getMaxY
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getMaxY
        (JNIEnv *, jobject);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setMinX
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setMinX
        (JNIEnv *, jobject, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setMinY
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setMinY
        (JNIEnv *, jobject, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setMaxX
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setMaxX
        (JNIEnv *, jobject, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setMaxY
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setMaxY
        (JNIEnv *, jobject, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setPoint
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setPoint
        (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    getPoint
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getPoint
        (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    setPointFigures
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_setPointFigures
        (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    getPointFigures
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_example_feodal_Pole_getPointFigures
        (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    checkTriangles
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_feodal_Pole_checkTriangles
        (JNIEnv *, jobject, jint,jint);

/*
 * Class:     com_example_feodal_Pole
 * Method:    isOurPoint
 * Signature: (III)Z
 */
JNIEXPORT jboolean JNICALL Java_com_example_feodal_Pole_isOurPoint
        (JNIEnv *, jobject, jint, jint, jint);

#ifdef __cplusplus
}
#endif

/*
 * Class:     com_example_feodal_Pole
 * Method:    CheckPointNotNeedFill
 * Signature: ([[ZIII)V
 */
void checkPointNotNeedFill(bool **, jint, jint, jint);

bool isOurPoint(jint, jint, jint);

#endif
