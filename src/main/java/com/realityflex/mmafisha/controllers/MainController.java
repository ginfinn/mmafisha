package com.realityflex.mmafisha.controllers;

import com.realityflex.mmafisha.config.jwt.JwtProvider;
import com.realityflex.mmafisha.config.jwt.UserService;
import com.realityflex.mmafisha.dto.dtofordate.Date;
import com.realityflex.mmafisha.dto.dtopreview.Preview;
import com.realityflex.mmafisha.dto.iteminfodto.ItemInfo;
import com.realityflex.mmafisha.dto.putSpheredto.PutSphere;
import com.realityflex.mmafisha.entity.*;
import com.realityflex.mmafisha.repository.*;
import com.realityflex.mmafisha.service.RestTemplateGetJson;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final SphereRepository sphereRepository;
    private final DistrictRepository districtRepository;
    private final AuditorieRepository auditorieRepository;
    private final SpotRepository spotRepository;
    private final ItemRepository itemRepository;
    private final RestTemplateGetJson restTemplateGetJson;
    private final UniqueAuditoreRepository uniqueAuditoreRepository;
    private final UniqueSphereRepository uniqueSphereRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public AuthRespons registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        Member u = new Member();
        u.setPassword(registrationRequest.getPassword());
        u.setMemberName(registrationRequest.getLogin());
        userService.saveUser(u);
        Member memberEntity = userService.findByLoginAndPassword(registrationRequest.getLogin(), registrationRequest.getPassword());
        String token = jwtProvider.generateToken(memberEntity.getMemberName());
        return new AuthRespons(token);
    }

    @PostMapping("/auth")
    public AuthRespons auth(@RequestBody AuthRequest request) {
        Member memberEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(memberEntity.getMemberName());
        return new AuthRespons(token);
    }

    @GetMapping("/user/a")
    public void getJson() {
        String page = "";

        val jsonForPageCount = restTemplateGetJson.getJson(page);
        val url = jsonForPageCount.get_links().getLast().getHref();
        val spliterator = url.split("page=");
        val pageCount = spliterator[1];
        for (int i = 1; i < Integer.parseInt(pageCount); i++) {
            val json = restTemplateGetJson.getJson(Integer.toString(i));
            for (val afisha : json.getItems()) {
                ArrayList<Auditorie> auditories = new ArrayList<>();
                ArrayList<District> districts = new ArrayList<>();
                ArrayList<Sphere> spheres = new ArrayList<>();
                ArrayList<Spot> spots = new ArrayList<>();
                for (val spot : afisha.getSpots()) {
                    Spot spotForList = spotRepository.save(Spot.builder().address(spot.getAddress())
                            .foundationId(spot.getFoundation_id())
                            .lat(spot.getLat())
                            .lon(spot.getLon()).build());
                    spots.add(spotForList);
                    for (val sphere : afisha.getSpheres()) {
                        Sphere sphereForList = sphereRepository.save(Sphere.builder().title(sphere.getTitle()).build());
                        spheres.add(sphereForList);
                    }
                    for (val district : afisha.getDistricts()) {
                        District districtForList = districtRepository.save(District.builder().districtId(district.getId()).build());
                        districts.add(districtForList);
                    }
                    for (val auditorie : afisha.getAuditories()) {
                        Auditorie auditorieForList = auditorieRepository.save(Auditorie.builder().title(auditorie.getTitle()).build());
                        auditories.add(auditorieForList);
                    }

                }
                val spliterator1 = afisha.getDate_from().split(" ");
                val spliterator2 = afisha.getDate_from().split(" ");
                itemRepository.save(Item.builder().age(afisha.getRestriction().getAge())
                        .dateFrom(spliterator1[0])
                        .dateTo(spliterator2[0])
                        .dateFromTimestamp(afisha.getDate_from_timestamp())
                        .dateToTimestamp(afisha.getDate_to_timestamp())
                        .free(afisha.getFree())
                        .status(afisha.getStatus())
                        .text(afisha.getText())
                        .title(afisha.getTitle())
                        .ulrImage(afisha.getImage().getSmall().getSrc())
                        .spots(spots)
                        .auditories(auditories)
                        .districts(districts)
                        .spheres(spheres)
                        .phone(afisha.getFoundation().getPhone())
                        .foundationTitle(afisha.getFoundation().getTitle()).build());


            }
        }


    }

    @GetMapping("/user/preview")
    public List<Preview> putPreview(int page, int size) {

        Pageable secondPageWithFiveElements = PageRequest.of(page, size);
        val pagePreview = itemRepository.findAllByAgeIsNotNull(secondPageWithFiveElements);
        List<Preview> previews = new ArrayList<>();
        for (val preview : pagePreview) {
            for (val spot : preview.getSpots()) {
                List<String> spheres = new ArrayList<>();
                Preview previewForList = new Preview();
                previewForList.setAddress(spot.getAddress());
                previewForList.setDate(preview.getDateFrom());
                previewForList.setLat(spot.getLat());
                previewForList.setLon(spot.getLon());
                previewForList.setTitle(preview.getTitle());
                previewForList.setDate_from_timestamp(preview.getDateFromTimestamp());
                previewForList.setFree(preview.getFree());
                previewForList.setIdItem(preview.getIdItem());
                for (val sphere : preview.getSpheres()) {
                    spheres.add(sphere.getTitle());
                }
                previewForList.setSphere(spheres);
                previewForList.setJpgUrl("https://www.mos.ru" + preview.getUlrImage());
                previews.add(previewForList);
            }
        }

        return previews;

    }

    @GetMapping("/user/findByDate")
    public List<Date> findByDate(int page, int size, String date) {
        List<Date> dates = new ArrayList<>();
        Pageable secondPageWithFiveElements = PageRequest.of(page, size);
        List<Item> itemByDate = itemRepository.findAllByDateFrom(date, secondPageWithFiveElements);
        for (val item : itemByDate) {
            List<String> spheres = new ArrayList<>();
            Date dateForList = new Date();
            dateForList.setDate(item.getDateFrom());
            dateForList.setFree(item.getFree());
            dateForList.setIdItem(item.getIdItem());
            dateForList.setTitle(item.getTitle());
            dateForList.setDate_from_timestamp(item.getDateFromTimestamp());
            for (val sphere : item.getSpheres()) {
                spheres.add(sphere.getTitle());
            }
            dateForList.setSphere(spheres);
            dates.add(dateForList);
        }

        return dates.stream().sorted(Comparator.comparing(Date::getDate_from_timestamp)).collect(Collectors.toList());

    }

    @GetMapping("/user/putIntem")
    public ItemInfo putItem(int id) {
        ItemInfo itemInfo = new ItemInfo();
        val item = itemRepository.findItemByIdItem(id);
        itemInfo.setJpgUrl(item.getUlrImage());
        itemInfo.setDate_from_timestamp(item.getDateFromTimestamp());
        itemInfo.setDate_from(item.getDateFrom());
        itemInfo.setText(item.getText());
        itemInfo.setTitle(item.getTitle());
        List<Spot> address = new ArrayList<>(item.getSpots());
        itemInfo.setAddress(address);
        itemInfo.setDate_to(item.getDateTo());
        itemInfo.setDate_to_timestamp(item.getDateToTimestamp());
        return itemInfo;
    }

    @GetMapping("/user/putSphere")
    public PutSphere putSphere() {

        Set<String> title = new HashSet<>();
        Set<String> auditoriesForResult = new HashSet<>();
        PutSphere putSphere = new PutSphere();

        val spheres = uniqueSphereRepository.findAll();
        val auditories = uniqueAuditoreRepository.findAll();
        for (val sphere : spheres) {
            title.add(sphere.getSphere());
        }
        for (val auditorie : auditories) {
            auditoriesForResult.add(auditorie.getAuditorie());
        }

        putSphere.setAuditories(auditoriesForResult);
        putSphere.setTitles(title);

        return putSphere;
    }
}

