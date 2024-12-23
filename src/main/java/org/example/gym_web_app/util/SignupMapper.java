package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.SignupDTO;
import org.example.gym_web_app.model.Signup;

public class SignupMapper {
    private SignupMapper() {

    }
    public static SignupDTO toDTO(Signup signup) {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setId(signup.getId());
        signupDTO.setMemberId(signup.getMember().getId());
        signupDTO.setClassScheduleId(signup.getClassSchedule().getId());
        signupDTO.setSignupTime(signup.getSignupTime());
        return signupDTO;
    }

    //
//    public static SignupMapper toDTO(Signup signup) {
//        SignupDTO dto = new SignupDTO();
//        dto.setId(signup.getId());
//        dto.setMemberId(signup.getMember() != null ? signup.getMember().getId() : null);
//        dto.setClassScheduleId(signup.getClassSchedule() != null ? signup.getClassSchedule().getId() : null);
//        dto.setSignupTime(signup.getSignupTime());
//        dto.setAttended(signup.isAttended());
//        return dto;
//    }

    public static Signup toEntity(SignupDTO dto) {
        Signup signup = new Signup();
        signup.setId(dto.getId());
        signup.setSignupTime(dto.getSignupTime());

        return signup;
    }
}
