#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>
#include <android/asset_manager_jni.h>
#include <android/log.h>

using namespace cv;
using namespace std;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_user_opencv_1app1_WaitingPicture4GC_loadImage(JNIEnv *env, jobject instance,
                                            jstring imageFileName_, jlong img) {
    const char *imageFileName = env->GetStringUTFChars(imageFileName_, 0);

    // TODO
    Mat &img_input = *(Mat *) img;

    const char *nativeFileNameString = env->GetStringUTFChars(imageFileName_, JNI_FALSE);

    string baseDir("/storage/emulated/0/");
    baseDir.append(nativeFileNameString);
    const char *pathDir = baseDir.c_str();

    img_input = imread(pathDir, IMREAD_COLOR);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_user_opencv_1app1_WaitingPicture4GC_imageprocessing(JNIEnv *env, jobject instance,
                                                  jlong inputImage,
                                                  jlong outputImage) {

    // TODO
    Mat &img_input = *(Mat *) inputImage;
    Mat &img_output = *(Mat *) outputImage;

    cvtColor(img_input, img_input, CV_BGR2RGB);
    cvtColor(img_input, img_output, CV_RGB2GRAY);
    equalizeHist(img_output, img_output);

    blur(img_output, img_output, Size(5, 5));
    Canny(img_output, img_output, 150, 200, 5);


    Mat_<uchar>image(img_output);
    Mat_<uchar>destImage(img_output.size());

    for(int y = 0 ; y < image.rows ; y++){
        for(int x = 0  ; x < image.cols; x++){
            uchar r = image(y,x);
            destImage(y,x) = 255 -r;
       }
    }

    img_output = destImage.clone();

    //GaussianBlur(img_output, img_output, Size(5,5), 0, 0);
    threshold( img_output, img_output, 127, 255, THRESH_BINARY );
}

