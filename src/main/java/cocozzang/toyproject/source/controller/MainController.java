package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.HexaDTO;
import cocozzang.toyproject.source.dto.UserDTO;
import cocozzang.toyproject.source.service.UserService;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) throws JSONException {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        UserDTO userdata = new UserDTO();
        List<HexaDTO> hexaDTOList = new ArrayList<>();
        if (!Objects.equals(id, "anonymousUser")) {

            userdata = userService.rtUser(id);

            String url = "https://open.api.nexon.com/maplestory/";
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .path("v1/id")
                    .queryParam("character_name", "유해삐")
                    .encode()
                    .build()
                    .toUri();

            RestTemplate restTemplate = new RestTemplate();

            RequestEntity<Void> request = RequestEntity
                    .get(uri)
                    .header("accept", "application/json")
                    .header("x-nxopen-api-key", " test_b390db4c42baa67a73f0de7a1b61b8cd97f64d7d146b6ea6ccb20c6df78c5f0d53b51e87bd231da191323d399d8e6cb9")
                    .build();

            ResponseEntity<String> entity = restTemplate.exchange(request, String.class);
            String res = entity.getBody();

            JSONObject jsonObject = new JSONObject(res);
            String ocid = jsonObject.getString("ocid");

            URI uri2 = UriComponentsBuilder.fromHttpUrl(url)
                    .path("v1/character/hexamatrix")
                    .queryParam("ocid", ocid)
                    .queryParam("date", "2024-01-10")
                    .encode()
                    .build()
                    .toUri();

            RestTemplate restTemplate2 = new RestTemplate();

            RequestEntity<Void> request2 = RequestEntity
                    .get(uri2)
                    .header("accept", "application/json")
                    .header("x-nxopen-api-key", " test_b390db4c42baa67a73f0de7a1b61b8cd97f64d7d146b6ea6ccb20c6df78c5f0d53b51e87bd231da191323d399d8e6cb9")
                    .build();

            ResponseEntity<String> entity2 = restTemplate2.exchange(request2, String.class);
            String res2 = entity2.getBody();
            JSONObject jsonObject2 = new JSONObject(res2);
            JSONArray jsonArray = jsonObject2.getJSONArray("character_hexa_core_equipment");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempObj = (JSONObject) jsonArray.get(i);
                HexaDTO hexaDTO = new HexaDTO();
                hexaDTO.setSkillname((String) tempObj.get("hexa_core_name"));
                hexaDTO.setSkilllevel(tempObj.get("hexa_core_level").toString());
                hexaDTOList.add(hexaDTO);
            }

        }

        model.addAttribute("id", id);
        model.addAttribute("role", role);
        //model.addAttribute("apikey", userdata.getApikey());
        model.addAttribute("hexaList", hexaDTOList);

        return "main";
    }

}
