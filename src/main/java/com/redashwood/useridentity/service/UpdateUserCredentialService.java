package com.redashwood.useridentity.service;

import com.redashwood.useridentity.dto.UpdateCredentialRequestTO;
import com.redashwood.useridentity.dto.UpdateCredentialResponseTO;

public interface UpdateUserCredentialService {

    UpdateCredentialResponseTO updateUserCredential(UpdateCredentialRequestTO updateCredentialRequestTO);
}
