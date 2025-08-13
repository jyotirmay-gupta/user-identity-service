package com.jyotirmay.useridentity.service;

import com.jyotirmay.useridentity.dto.UpdateCredentialRequestTO;
import com.jyotirmay.useridentity.dto.UpdateCredentialResponseTO;

public interface UpdateUserCredentialService {

    UpdateCredentialResponseTO updateUserCredential(UpdateCredentialRequestTO updateCredentialRequestTO);
}
