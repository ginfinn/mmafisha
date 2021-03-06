package com.realityflex.mmafisha.controllers;

import com.realityflex.mmafisha.config.jwt.JwtProvider;
import com.realityflex.mmafisha.config.jwt.UserService;
import com.realityflex.mmafisha.dto.dotforuserupdate.UserUpdate;
import com.realityflex.mmafisha.dto.dtofordate.Date;
import com.realityflex.mmafisha.dto.dtoforsetpost.SetPost;
import com.realityflex.mmafisha.dto.dtogetpost.CommentDto;
import com.realityflex.mmafisha.dto.dtogetpost.GetPost;
import com.realityflex.mmafisha.dto.dtopreview.Preview;
import com.realityflex.mmafisha.dto.iteminfodto.ItemInfo;
import com.realityflex.mmafisha.dto.putSpheredto.PutSphere;
import com.realityflex.mmafisha.dto.putSpheredto.PutSphereAndSubscriptionForUser;
import com.realityflex.mmafisha.entity.*;
import com.realityflex.mmafisha.repository.*;
import com.realityflex.mmafisha.service.RestTemplateGetJson;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    private final MemberRepository memberRepository;
    private final SubscriptionsRepository subscriptionsRepository;
    private final PostRepository postRepository;

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public Object registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        Member u = new Member();
        u.setPassword(registrationRequest.getPassword());
        u.setMemberName(registrationRequest.getLogin());
        if (memberRepository.existsByMemberName(registrationRequest.getLogin())) {
            return "?????????? ???????????????????????? ?????? ????????????????????";
        } else {

            userService.saveUser(u);
            Member memberEntity = userService.findByLoginAndPassword(registrationRequest.getLogin(), registrationRequest.getPassword());
            String token = jwtProvider.generateToken(memberEntity.getMemberName());
            return new AuthResponse(token);
        }
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        Member memberEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(memberEntity.getMemberName());
        return new AuthResponse(token);
    }

    @GetMapping("/fillDb")
    public void getJson() {
        String page = "";

        val jsonForPageCount = restTemplateGetJson.getJson(page);
        val url = jsonForPageCount.get_links().getLast().getHref();
        val spliterator = url.split("page=");
        val pageCount = spliterator[1];
        for (int i = 1; i < Integer.parseInt(pageCount); i++) {
            val json = restTemplateGetJson.getJson(Integer.toString(i));
            for (val poster : json.getItems()) {
                ArrayList<Auditorie> auditories = new ArrayList<>();
                ArrayList<District> districts = new ArrayList<>();
                ArrayList<Sphere> spheres = new ArrayList<>();
                ArrayList<Spot> spots = new ArrayList<>();
                for (val spot : poster.getSpots()) {
                    Spot spotForList = spotRepository.save(Spot.builder().address(spot.getAddress())
                            .foundationId(spot.getFoundation_id())
                            .lat(spot.getLat())
                            .lon(spot.getLon()).build());
                    spots.add(spotForList);
                    for (val sphere : poster.getSpheres()) {
                        Sphere sphereForList = sphereRepository.save(Sphere.builder().title(sphere.getTitle()).build());
                        spheres.add(sphereForList);
                    }
                    for (val district : poster.getDistricts()) {
                        District districtForList = districtRepository.save(District.builder().districtId(district.getId()).build());
                        districts.add(districtForList);
                    }
                    for (val auditorie : poster.getAuditories()) {
                        Auditorie auditorieForList = auditorieRepository.save(Auditorie.builder().title(auditorie.getTitle()).build());
                        auditories.add(auditorieForList);
                    }

                }
                val spliterator1 = poster.getDate_from().split(" ");
                val spliterator2 = poster.getDate_from().split(" ");
                itemRepository.save(Item.builder().age(poster.getRestriction().getAge())
                        .dateFrom(spliterator1[0])
                        .dateTo(spliterator2[0])
                        .dateFromTimestamp(poster.getDate_from_timestamp())
                        .dateToTimestamp(poster.getDate_to_timestamp())
                        .free(poster.getFree())
                        .status(poster.getStatus())
                        .text(poster.getText())
                        .title(poster.getTitle())
                        .ulrImage(poster.getImage().getSmall().getSrc())
                        .spots(spots)
                        .auditories(auditories)
                        .districts(districts)
                        .spheres(spheres)
                        .phone(poster.getFoundation().getPhone())
                        .foundationTitle(poster.getFoundation().getTitle()).id(poster.getId()).build());


            }
        }


    }

    @GetMapping("/preview")
    public List<Preview> putPreview(int page, int size) {

        Pageable secondPageWithFiveElements = PageRequest.of(page, size);
        val pagePreview = itemRepository.findAllByAgeIsNotNull(secondPageWithFiveElements);
        List<Preview> previews = new ArrayList<>();
        GeneratePreview(pagePreview, previews);

        return previews;

    }


    @GetMapping("/findByDate")
    public List<Date> findByDate(int page, int size, String date) {
        List<Date> dates = new ArrayList<>();
        Pageable secondPageWithFiveElements = PageRequest.of(page, size);
        List<Item> itemByDate = itemRepository.findAllByDateFrom(date, secondPageWithFiveElements);
        for (val item : itemByDate) {
            List<String> spheres = new ArrayList<>();
            Date dateForList = new Date();
            dateForList.setDate(item.getDateFrom());
            dateForList.setFree(item.getFree());
            dateForList.setIdItem(item.getId());
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

    @GetMapping("/putItem")
    public ItemInfo putItem(int id) {
        ItemInfo itemInfo = new ItemInfo();
        val item = itemRepository.findItemById(id);
        itemInfo.setJpgUrl("https://www.mos.ru" + item.getUlrImage());
        itemInfo.setDate_from_timestamp(item.getDateFromTimestamp());
        itemInfo.setDate_from(item.getDateFrom());
        itemInfo.setText(item.getText());
        itemInfo.setTitle(item.getTitle());
        List<Spot> address = new ArrayList<>(item.getSpots());
        itemInfo.setAddress(address);
        itemInfo.setDate_to(item.getDateTo());
        itemInfo.setDate_to_timestamp(item.getDateToTimestamp());
        itemInfo.setPhone(item.getPhone());
        return itemInfo;
    }

    @GetMapping("/putSphere")
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

    @GetMapping("/onetime/putSphere")
    public PutSphere Sphere() {

        Set<String> titles = new HashSet<>();
        Set<String> auditoriesForResult = new HashSet<>();
        PutSphere putSphere = new PutSphere();

        val spheres = sphereRepository.findAll();
        val auditories = auditorieRepository.findAll();
        for (val sphere : spheres) {
            titles.add(sphere.getTitle());
        }
        for (val title : titles) {
            uniqueSphereRepository.save(UniqueSphere.builder().sphere(title).build());
        }
        for (val auditorie : auditories) {
            auditoriesForResult.add(auditorie.getTitle());

        }
        for (val auditorieForResult : auditoriesForResult) {
            uniqueAuditoreRepository.save(UniqueAuditore.builder().auditorie(auditorieForResult).build());
        }

        putSphere.setAuditories(auditoriesForResult);
        putSphere.setTitles(titles);

        return putSphere;
    }

    @GetMapping("/user/putSphere")
    public PutSphereAndSubscriptionForUser putSphereAndSubscriptionForUser(@RequestHeader("Authorization") String token) {
        String memberName = decoder(token);
        List<String> spheresForResult = new ArrayList<>();
        List<String> auditoriesForResult = new ArrayList<>();
        List<String> subscriptionsForResult = new ArrayList<>();
        PutSphereAndSubscriptionForUser putSphereAndSubscriptionForUser = new PutSphereAndSubscriptionForUser();
        val spheres = uniqueSphereRepository.findAll();
        val auditories = uniqueAuditoreRepository.findAll();
        val subscriptions = subscriptionsRepository.findAllByMemberId(memberRepository.findByMemberName(memberName).getMemberId());
        for (val sphere : spheres) {
            spheresForResult.add(sphere.getSphere());
        }
        for (val auditore : auditories) {
            auditoriesForResult.add(auditore.getAuditorie());
        }
        for (val subscription : subscriptions) {
            subscriptionsForResult.add(subscription.getSphere());
        }
        putSphereAndSubscriptionForUser.setSubscription(subscriptionsForResult);
        putSphereAndSubscriptionForUser.setAuditories(auditoriesForResult);
        putSphereAndSubscriptionForUser.setSphere(spheresForResult);
        putSphereAndSubscriptionForUser.setUserName(memberName);
        return putSphereAndSubscriptionForUser;

    }

    @Transactional
    @PostMapping("/user/userSubscription")
    public void userSubscription(@RequestHeader("Authorization") String token, @RequestParam String sphere) {
        String memberName = decoder(token);

        val member = memberRepository.findMemberByMemberName(memberName);

        if (subscriptionsRepository.existsBySphereAndMemberId(sphere, member.getMemberId())) {
            subscriptionsRepository.deleteBySphere(sphere);
        } else {
            val subscriptions = Subscriptions.builder().sphere(sphere).memberId(member.getMemberId()).build();
            member.getSubscriptions().add(subscriptions);
            memberRepository.save(member);
        }


    }

    @PostMapping("/user/userUpdate")
    public void userUpdate(@RequestHeader("Authorization") String token, @RequestBody UserUpdate userUpdate) {
        String memberName = decoder(token);
        val member = memberRepository.findByMemberName(memberName);
        member.setAvatarUrl(userUpdate.getAvatarUrl());
        member.setText(userUpdate.getText());
        member.setName(userUpdate.getName());
        memberRepository.save(member);

    }

    @PostMapping("/user/setPost")
    public void setPost(@RequestHeader("Authorization") String token, @RequestBody SetPost setpost) {
        String memberName = decoder(token);
        val member = memberRepository.findByMemberName(memberName);
        val postsForResult = new Post();
        postsForResult.setName(member.getName());


        IdItemForPost idItemForPost1 = new IdItemForPost();
        List<IdItemForPost> idItemForPosts = new ArrayList<>();


        for (val itemId : setpost.getItemId()) {
            idItemForPost1.setItemId(itemId);
            idItemForPosts.add(idItemForPost1);
        }
        postsForResult.setText(setpost.getText());
        postsForResult.setTitle(setpost.getTitle());
        postsForResult.setIdItemForPosts(idItemForPosts);
        postsForResult.setJpgUrl(setpost.getJpgUrl());
        member.getPosts().add(postsForResult);
        memberRepository.save(member);
    }

    @GetMapping("/user/getUser")
    public Member getUser(@RequestHeader("Authorization") String token) {
        String memberName = decoder(token);
        return memberRepository.findByMemberName(memberName);
    }

    @PostMapping("/user/addComment")
    public void addComment(@RequestHeader("Authorization") String token, @RequestParam String message, @RequestParam Integer postId) {
        String memberName = decoder(token);
        val member = memberRepository.findByMemberName(memberName);
        val post = postRepository.findByPostId(postId);
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setName(member.getName());
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        post.setComments(comments);
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        member.setPosts(posts);
        memberRepository.save(member);
    }

    @GetMapping("/user/getPost")
    public GetPost getPost(Integer postId) {
        val post = postRepository.findByPostId(postId);
        List<Preview> previews = new ArrayList<>();
        CommentDto commentDto = new CommentDto();
        List<CommentDto> comments = new ArrayList<>();
        GetPost getPost = new GetPost();
        getPost.setPostId(postId);
        getPost.setJpgUrl(post.getJpgUrl());
        for (val comment : post.getComments()) {
            commentDto.setMessage(comment.getMessage());
            commentDto.setName(comment.getName());
            comments.add(commentDto);
        }
        for (val itemId : post.getIdItemForPosts()) {
            val pagePreview = itemRepository.findAllItemById(itemId.getItemId());
            GeneratePreview(pagePreview, previews);
        }
        getPost.setPreviews(previews);
        getPost.setDate(post.getDate());
        getPost.setName(post.getName());
        getPost.setCommentDtos(comments);
        getPost.setTitle(post.getTitle());
        getPost.setText(post.getText());
        return getPost;
    }

    @GetMapping("/user/getPostByName")
    public GetPost getPostByName(String name) {
        val posts = postRepository.findAllByName(name);
        List<Preview> previews = new ArrayList<>();
        CommentDto commentDto = new CommentDto();
        List<CommentDto> comments = new ArrayList<>();
        GetPost getPost = new GetPost();
        for (val post : posts) {

            getPost.setPostId(post.getPostId());
            getPost.setJpgUrl(post.getJpgUrl());
            for (val comment : post.getComments()) {
                commentDto.setMessage(comment.getMessage());
                commentDto.setName(comment.getName());
                comments.add(commentDto);
            }
        }
        for (val post : posts) {
            for (val itemId : post.getIdItemForPosts()) {
                val pagePreview = itemRepository.findAllItemById(itemId.getItemId());
                GeneratePreview(pagePreview, previews);
                getPost.setPreviews(previews);
                getPost.setDate(post.getDate());
                getPost.setName(post.getName());
                getPost.setCommentDtos(comments);
                getPost.setTitle(post.getTitle());
                getPost.setText(post.getText());
            }
        }

        return getPost;

    }

    private void GeneratePreview(List<Item> pagePreview, List<Preview> previews) {
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
                previewForList.setIdItem(preview.getId());
                for (val sphere : preview.getSpheres()) {
                    spheres.add(sphere.getTitle());
                }
                previewForList.setSphere(spheres);
                previewForList.setJpgUrl("https://www.mos.ru" + preview.getUlrImage());
                previews.add(previewForList);
            }
        }
    }

    public String decoder(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        String[] chunks;
        chunks = token.split("Bearer ");
        String a = chunks[1];
        String[] chunks2 = a.split("\\.");
        String[] v;
        String payload = new String(decoder.decode(chunks2[1]));
        String[] c = payload.split(":");
        v = c[1].split(",");
        String memberName = v[0];
        memberName = memberName.replace("\"", "");
        return memberName;

    }

}





