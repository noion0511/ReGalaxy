package com.regalaxy.phonesin.address.model.repository;

import com.regalaxy.phonesin.address.model.AddressDto;
import com.regalaxy.phonesin.address.model.AgencyDto;
import com.regalaxy.phonesin.address.model.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    @Autowired
    private final EntityManager em;

    @Override
    public List<AddressDto> addressList(Long member_id) {
        String s = "select new com.regalaxy.phonesin.address.model.AddressDto(a.addressId, a.address) "
                + "from address a "
                + "where a.member.memberId = " + member_id;
        return em.createQuery(s, AddressDto.class).getResultList();
    }

    @Override
    public List<AgencyDto> samsungList(LocationDto locationDto) {
        String s = "select new com.regalaxy.phonesin.address.model.AgencyDto(a.agencyPhoneNum, a.agencyLocation, a.agencyName, a.agencyPhotoUrl, a.agencyX, a.agencyY, a.agencyX) "
                + "from agency a "
                + "order by (a.agencyY-" + locationDto.getLatitude() + ")*(a.agencyY-"+locationDto.getLatitude()+")+(a.agencyX-"+locationDto.getLongitude()+")*(a.agencyX-"+locationDto.getLongitude()+")";
        return em.createQuery(s, AgencyDto.class).getResultList();
    }

    @Override
    public List<AgencyDto> samsungListSearch(LocationDto locationDto) {
        String s = "select new com.regalaxy.phonesin.address.model.AgencyDto(a.agencyPhoneNum, a.agencyLocation, a.agencyName, a.agencyPhotoUrl, a.agencyX, a.agencyY, a.agencyX) "
                + "from agency a "
                + "where agencyLocation like '%"+locationDto.getSearch()+"%'"
                + "order by (a.agencyY-" + locationDto.getLatitude() + ")*(a.agencyY-"+locationDto.getLatitude()+")+(a.agencyX-"+locationDto.getLongitude()+")*(a.agencyX-"+locationDto.getLongitude()+")";
        return em.createQuery(s, AgencyDto.class).getResultList();
    }
}
