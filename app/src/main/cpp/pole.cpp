#include "pole.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_create(JNIEnv *env, jobject clazz, int quantity_points) {
    quantityPoints_ = quantity_points;
    minX_ = quantity_points - 1;
    minY_ = quantity_points - 1;

    points_ = new jint *[quantity_points];
    pointsFigures_ = new jint *[quantity_points];
    for (jint i = 0; i < quantity_points; i++) {
        points_[i] = new jint[quantity_points];
        pointsFigures_[i] = new jint[quantity_points];
    }
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_destroy(JNIEnv *env, jobject clazz) {
    for (jint i = 0; i < quantityPoints_; i++) {
        delete[]points_[i];
        delete[]pointsFigures_[i];
    }
    delete[]points_;
    delete[]pointsFigures_;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getMinX(JNIEnv *env, jobject clazz) {
    return minX_;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getMinY(JNIEnv *env, jobject clazz) {
    return minY_;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getMaxX(JNIEnv *env, jobject clazz) {
    return maxX_;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getMaxY(JNIEnv *env, jobject clazz) {
    return maxY_;
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setMinX(JNIEnv *env, jobject clazz, jint x) {
    minX_ = x;
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setMinY(JNIEnv *env, jobject clazz, jint y) {
    minY_ = y;
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setMaxX(JNIEnv *env, jobject clazz, jint x) {
    maxX_ = x;
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setMaxY(JNIEnv *env, jobject clazz, jint y) {
    maxY_ = y;
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setPoint(JNIEnv *env, jobject clazz, jint y, jint x, jint point) {
    points_[y][x] = point;
    pointsFigures_[y][x] = point;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getPoint(JNIEnv *env, jobject clazz, jint y, jint x) {
    return points_[y][x];
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_setPointFigures(JNIEnv *env, jobject clazz, jint y, jint x,
                                             jint point) {
    pointsFigures_[y][x] = point;
}

JNIEXPORT jint JNICALL
Java_com_example_feodal_Pole_getPointFigures(JNIEnv *env, jobject clazz, jint y, jint x) {
    return pointsFigures_[y][x];
}

JNIEXPORT void JNICALL
Java_com_example_feodal_Pole_checkTriangles(JNIEnv *env, jobject clazz, jint point, jint point1) {

    jint sizeY = maxY_ - minY_ + 1;
    jint sizeX = maxX_ - minX_ + 1;

    bool **isPointNotNeedFill = new bool *[sizeY];
    for (jint i = 0; i < sizeY; i++) {
        isPointNotNeedFill[i] = new bool[sizeX];
    }

    for (jint y = 0; y < sizeY; y++) {
        isPointNotNeedFill[y][0] = !isOurPoint(y + minY_, minX_, point);
        isPointNotNeedFill[y][sizeX - 1] = !isOurPoint(y + minY_, maxX_, point);
    }

    for (jint x = 0; x < sizeX; x++) {
        isPointNotNeedFill[0][x] = !isOurPoint(minY_, x + minX_, point);
        isPointNotNeedFill[sizeY - 1][x] = !isOurPoint(maxY_, x + minX_, point);
    }

    for (jint y = 1; y < sizeY - 1; y++) {
        for (jint x = 1; x < sizeX - 1; x++) {
            checkPointNotNeedFill(isPointNotNeedFill, y, x, point);
            checkPointNotNeedFill(isPointNotNeedFill, y, sizeX - x - 1, point);
            checkPointNotNeedFill(isPointNotNeedFill, sizeY - y - 1, x, point);
            checkPointNotNeedFill(isPointNotNeedFill, sizeY - y - 1, sizeX - x - 1, point);
        }
    }

//    for (jint y = minY_ + 1; y < maxY_; y++) {
//        for (jint x = minX_ + 1; x < maxX_; x++) {
//            if (!isPointNotNeedFill[y - minY_][x - minX_] && !isOurPoint(y, x, point)) {
//                pointsFigures_[y][x] = point;
//            }
//        }
//    }

    for (jint y = minY_; y <= maxY_; y++) {
        for (jint x = minX_; x <= maxX_; x++) {
            if (!isPointNotNeedFill[y - minY_][x - minX_] && !isOurPoint(y, x, point)) {
                pointsFigures_[y][x] = point;
            } else {
                if(isPointNotNeedFill[y - minY_][x - minX_]) {
                    points_[y][x] = point1;
                }
            }
        }
    }

    for (jint i = 0; i < sizeY; i++) {
        delete[]isPointNotNeedFill[i];
    }
    delete[]isPointNotNeedFill;
}

JNIEXPORT jboolean JNICALL
Java_com_example_feodal_Pole_isOurPoint(JNIEnv *env, jobject clazz, jint y, jint x, jint point) {
    return isOurPoint(y, x, point);
}

#ifdef __cplusplus
}
#endif

void checkPointNotNeedFill(bool **is_point_not_need_fill, jint y, jint x, jint point) {
    if (!is_point_not_need_fill[y][x] && !isOurPoint(y + minY_, x + minX_, point)) {
        is_point_not_need_fill[y][x] = (is_point_not_need_fill[y - 1][x]
                                        || is_point_not_need_fill[y][x - 1]
                                        || is_point_not_need_fill[y + 1][x]
                                        || is_point_not_need_fill[y][x + 1]);
    }
}

bool isOurPoint(jint y, jint x, jint point) {
    return (y < 0 || y >= quantityPoints_ || x < 0 || x >= quantityPoints_)
            ? false
            : (pointsFigures_[y][x] == point);
}

