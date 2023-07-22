package com.ossbar.modules.evgl.eao.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.modules.evgl.eao.api.TeaoTraineeSignupService;
import com.ossbar.modules.evgl.eao.domain.TeaoTraineeSignup;
import com.ossbar.modules.evgl.eao.persistence.TeaoTraineeSignupMapper;
import com.ossbar.utils.tool.Identities;

@Service(version = "1.0.0")
public class TeaoTraineeSignupServiceImpl implements TeaoTraineeSignupService {

    @Autowired
    private TeaoTraineeSignupMapper teaoTraineeSignupMapper;

    @Override
    public void save(TeaoTraineeSignup teaoTraineeSignup) throws OssbarException {
        teaoTraineeSignup.setSignupId(Identities.uuid());
        teaoTraineeSignupMapper.insert(teaoTraineeSignup);
    }
}
