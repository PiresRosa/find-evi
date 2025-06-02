package com.dnd.spaced.global.security.core;

import com.dnd.spaced.domain.account.domain.Role;
import com.dnd.spaced.global.security.attribute.AbstractOAuth2Attribute;
import com.dnd.spaced.global.security.attribute.OAuth2AttributeConverter;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String type = userRequest.getClientRegistration()
                                 .getRegistrationId();
        String attributeKey = userRequest.getClientRegistration()
                                         .getProviderDetails()
                                         .getUserInfoEndpoint()
                                         .getUserNameAttributeName();
        AbstractOAuth2Attribute oAuth2Attribute = OAuth2AttributeConverter.convert(
                type,
                oAuth2User.getAttributes(),
                attributeKey
        );

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())),
                oAuth2Attribute.convert(),
                attributeKey
        );
    }
}
