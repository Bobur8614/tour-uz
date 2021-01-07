package uz.com.utils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.CustomUserDetails;
import uz.com.enums.LanguageType;
import uz.com.enums.RoleType;
import uz.com.hibernate.domain.auth.Role;
import uz.com.hibernate.domain.auth.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class UserSession {

    HttpServletRequest request;
    BaseUtils utils;

    @Autowired
    public UserSession(HttpServletRequest request, BaseUtils utils) {
        this.request = request;
        this.utils = utils;
    }

    public User getUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                user = (User) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            }
        }
        return user;
    }

    public String getLangCode() {
        if (!utils.isEmpty(getUser().getLanguage()))
            return getUser().getLanguage().getCode();
        else
            return LanguageType.RU.name().toLowerCase();
    }

    public Boolean getAllInfo() {
        Collection<Role> roles = getUser().getRoles();
        List<String> list1 = new ArrayList<>();
        roles.forEach(role -> {
            list1.add(role.getCode());
        });
        List<String> list = Arrays.asList(RoleType.SYS_ADMIN.code, RoleType.ADMIN.code,
                RoleType.SUPPLY_ADMIN.code, RoleType.SUPPLY_RESPONSIBLE.code, RoleType.FINANCE_HEAD.code, RoleType.FINANCE_ADMIN.code, RoleType.VIEW.code, RoleType.FOUNDER.code);
        return CollectionUtils.containsAny(list1, list);
    }



    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
