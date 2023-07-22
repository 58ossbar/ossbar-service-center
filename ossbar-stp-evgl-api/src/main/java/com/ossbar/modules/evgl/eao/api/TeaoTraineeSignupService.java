package com.ossbar.modules.evgl.eao.api;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.modules.evgl.eao.domain.TeaoTraineeSignup;

public interface TeaoTraineeSignupService {

    void save(TeaoTraineeSignup teaoTraineeSignup) throws OssbarException;

}
