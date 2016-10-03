/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   remoteFunctions.h
 * Author: Will Shackleford {@literal <william.shackleford@nist.gov>}
 *
 * Created on September 29, 2016, 9:59 AM
 */

#ifndef REMOTEFUNCTIONS_H
#define REMOTEFUNCTIONS_H

#ifdef __cplusplus
extern "C" {
#endif

    enum RemoteFunctionGroup {
        INVALID_FUNCTION_GROUP = 0,
        MOT_FUNCTION_GROUP = 1
    };

    enum RemoteMotFunctionType {
        MOT_INVALID = 0,
        MOT_START = 1,
        MOT_STOP = 2,
        MOT_TARGET_CLEAR = 3,
        MOT_JOINT_TARGET_SEND = 4,
        MOT_COORD_TARGET_SEND = 5,
        MOT_TARGET_RECEIVE = 6,
        MOT_SET_COORD = 7,
        MOT_SET_TOOL = 8,
        MOT_SET_SPEED = 9,
        MOT_SET_ORIGIN = 10,
        MOT_SET_TASK = 11,
        MOT_SET_SYNC = 12,
        MOT_RESET_SYNC = 13
    };

    // Read requests on the given accepted socket handle, forever or until an
    // error occurs.
    extern void handleSingleConnection(int acceptHandle);
    
    // Call the appropriate mot related function and send a response on the accepted handle.
    // Note: Return 0 for successs, any other return value is treated as a fatal error 
    // closing the socket.
    extern int handleMotFunctionRequest(int acceptHandle,char *inBuffer,char *outBuffer,int type);

#ifdef __cplusplus
}
#endif

#endif /* REMOTEFUNCTIONS_H */
