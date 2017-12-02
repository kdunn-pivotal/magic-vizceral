package io.pivotal.pde.magicvizceral.controllers;

import io.pivotal.pde.magicvizceral.model.VizceralDatasetPayload;
import io.pivotal.pde.magicvizceral.repositories.IVizPayloadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@EnableWebMvc
//@Configuration
//@ComponentScan({"com.baeldung.freemarker"})
@Controller
public class UiController {

    @Autowired
    private SimpMessagingTemplate webSocketMessageTemplate;

    @Autowired
    private IVizPayloadRepository vizPayloadRepository;

    private Long lastTimestamp = -1L;

    private static final Logger LOG = LoggerFactory.getLogger(UiController.class);

    private String DATA;

    @PostConstruct
    private void populateSampleData() {
        this.DATA = "{\n" +
                "  \"renderer\": \"global\",\n" +
                "  \"name\": \"edge\",\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"renderer\": \"region\",\n" +
                "      \"name\": \"INTERNET\",\n" +
                "      \"class\": \"normal\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"renderer\": \"region\",\n" +
                "      \"name\": \"us-east-1\",\n" +
                "      \"maxVolume\": 50000,\n" +
                "      \"class\": \"normal\",\n" +
                "      \"updated\": 1466838546805,\n" +
                "      \"nodes\": [\n" +
                "        {\n" +
                "          \"name\": \"INTERNET\",\n" +
                "          \"renderer\": \"focusedChild\",\n" +
                "          \"class\": \"normal\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"proxy-prod\",\n" +
                "          \"renderer\": \"focusedChild\",\n" +
                "          \"class\": \"normal\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"connections\": [\n" +
                "        {\n" +
                "          \"source\": \"INTERNET\",\n" +
                "          \"target\": \"proxy-prod\",\n" +
                "          \"metrics\": {\n" +
                "            \"danger\": 116.524,\n" +
                "            \"normal\": 15598.906\n" +
                "          },\n" +
                "          \"class\": \"normal\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"connections\": [\n" +
                "    {\n" +
                "      \"source\": \"INTERNET\",\n" +
                "      \"target\": \"us-east-1\",\n" +
                "      \"metrics\": {\n" +
                "        \"normal\": 26037.626,\n" +
                "        \"danger\": 92.37\n" +
                "      },\n" +
                "      \"notices\": [\n" +
                "      ],\n" +
                "      \"class\": \"normal\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        this.DATA = "{\n" +
                "\"renderer\": \"global\",\n" +
                "\"name\": \"edge\",\n" +
                "\"displayName\": \"edge\",\n" +
                "\"connections\": [{\n" +
                    "\"source\": \"INTERNET\",\n" +
                    "\"target\": \"BTC\"\n" +
                "}],\n" +
                    "\"nodes\": [{\n" +
                    "\"renderer\": \"region\",\n" +
                    "\"name\": \"INTERNET\",\n" +
                    "\"displayName\": \"INTERNET\"\n" +
                "}, {\n" +
                    "\"renderer\": \"region\",\n" +
                    "\"name\": \"BTC\",\n" +
                    "\"displayName\": \"BTC\",\n" +
                    "\"nodes\": [{\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"displayName\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\",\n" +
                        "\"displayName\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"displayName\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\",\n" +
                        "\"displayName\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"displayName\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\",\n" +
                        "\"displayName\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"displayName\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\",\n" +
                        "\"displayName\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"displayName\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\",\n" +
                        "\"displayName\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"displayName\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"displayName\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"displayName\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"displayName\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"displayName\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"displayName\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"displayName\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"displayName\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"displayName\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"displayName\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\",\n" +
                        "\"displayName\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"displayName\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\",\n" +
                        "\"displayName\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"displayName\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\",\n" +
                        "\"displayName\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"displayName\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\",\n" +
                        "\"displayName\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"displayName\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\",\n" +
                        "\"displayName\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"displayName\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\",\n" +
                        "\"displayName\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"displayName\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\",\n" +
                        "\"displayName\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"displayName\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\",\n" +
                        "\"displayName\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"displayName\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\",\n" +
                        "\"displayName\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"displayName\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\",\n" +
                        "\"displayName\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"displayName\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\",\n" +
                        "\"displayName\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\",\n" +
                        "\"displayName\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\",\n" +
                        "\"displayName\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\",\n" +
                        "\"displayName\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\",\n" +
                        "\"displayName\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\",\n" +
                        "\"displayName\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"displayName\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\",\n" +
                        "\"displayName\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"displayName\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\",\n" +
                        "\"displayName\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"displayName\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\",\n" +
                        "\"displayName\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"displayName\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\",\n" +
                        "\"displayName\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"displayName\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\",\n" +
                        "\"displayName\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\",\n" +
                        "\"displayName\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\",\n" +
                        "\"displayName\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\",\n" +
                        "\"displayName\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\",\n" +
                        "\"displayName\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"displayName\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\",\n" +
                        "\"displayName\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"displayName\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\",\n" +
                        "\"displayName\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"displayName\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\",\n" +
                        "\"displayName\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"displayName\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\",\n" +
                        "\"displayName\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"displayName\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\",\n" +
                        "\"displayName\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"displayName\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\",\n" +
                        "\"displayName\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"displayName\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\",\n" +
                        "\"displayName\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"displayName\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\",\n" +
                        "\"displayName\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"displayName\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\",\n" +
                        "\"displayName\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"displayName\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\",\n" +
                        "\"displayName\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"displayName\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\"\n" +
                        "}, {\n" +
                        "\"renderer\": \"focusedChild\",\n" +
                        "\"name\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\",\n" +
                        "\"displayName\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                        "}],\n" +
                    "\"connections\": [{\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 1536.0\n" +
                        "},\n" +
                        "\"source\": \"1a524fa1b71f405de9e2816b9412917791634a6e7e9189e2dcab44783ec85c5b\",\n" +
                        "\"target\": \"1BCYpzZAmH3pX7EXU6s4gxtG1AoVMn2NfJ\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 1152.0\n" +
                        "},\n" +
                        "\"source\": \"85953fe4cc76ecbe697e078cb5876b8354994ff486f318e9400e62eb163a3661\",\n" +
                        "\"target\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 1024.0\n" +
                        "},\n" +
                        "\"source\": \"8397302f8593709d4d974710c08feba56edc918f8f8d17f88f54f2a5b4037a1b\",\n" +
                        "\"target\": \"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 642.0\n" +
                        "},\n" +
                        "\"source\": \"1JqoNp7rBXe9fosS9bgbV37fnPNe2PE9o\",\n" +
                        "\"target\": \"c4cb1a7d832db467be149a3fd307477ae0c43afe7bf12766b8c5f7a1481307db\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 584.0\n" +
                        "},\n" +
                        "\"source\": \"18rdKmjrg1EawxgiVT3ikLExj6GWS2MNCk\",\n" +
                        "\"target\": \"512ddedfb05fc0ff9e930c107b919d6ba1a753821a1cc8138c7d73ea3673201c\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 561.0\n" +
                        "},\n" +
                        "\"source\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"target\": \"093804e038c7a789a80a34054c9265cb276919163bc1f86b8358241bf309b36b\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 536.0\n" +
                        "},\n" +
                        "\"source\": \"1EozGDpnr5XrRaDDN7aj44S8ieexyrFm92\",\n" +
                        "\"target\": \"d37161fcf54f94b85f265aed4e54f03dca513c4afe8d7f026d580f88a25ee2e7\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 511.0\n" +
                        "},\n" +
                        "\"source\": \"1F3Suhn3aEtwQ2CytzUZ5rxkGFpSvLa9Dg\",\n" +
                        "\"target\": \"8985d1e2affab97d0aff45b92d759c678bca55c8c50c076afbea062cfa28dc1a\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 501.0\n" +
                        "},\n" +
                        "\"source\": \"1EhEpXNhKNNz9VPhuvSUMjNY95irSDXfxN\",\n" +
                        "\"target\": \"e81dd0dc7505cb48f70298febafc2fd8bc3323d631d3826a2e591e8d66daf73f\"\n" +
                        "}, {\n" +
                        "\"class\": \"normal\",\n" +
                        "\"updated\": 1511824734837,\n" +
                        "\"metrics\": {\n" +
                        "\"normal\": 500.0\n" +
                        "},\n" +
                        "\"source\": \"52984df5832d16083a9a3e13b4af0224ae4c9837188512ebbb0ac0ec14bfaf4c\",\n" +
                        "\"target\": \"1F89hmmrtonJfAQNAqDmeDadcw7AsZcvXG\"\n" +
                    "}]\n" +
                "}],\n" +
                "\"server_update_time\": 1511824734837\n" +
                "}";
    }

    @GetMapping("/")
    public String index(/*@ModelAttribute("model") ModelMap model*/) {
        //model.addAttribute("initialdata", DATA /*vizPayloadRepository.getLatestPageRankPayload().toString()*/);
        LOG.debug("In index() / webroot");
        /* static/templates/index.ftl */
        return "index";
    }

    //@GetMapping("/sampleData.json")

    @RequestMapping(value = "/sampleData.json", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody String sampleData() throws Exception {
        //model.addAttribute("initialdata", DATA /*vizPayloadRepository.getLatestPageRankPayload().toString()*/);
        LOG.debug("In sampleData()");
        /* webapp/index.ftl */

        VizceralDatasetPayload simplePayload = vizPayloadRepository.getLatestPageRankPayload();
        //return DATA;

        //LOG.debug(simplePayload.toJSON());

        return simplePayload.toJSON();
                // general POJO naming fixup
        //        .replace("class_name", "class");
        /*
                // the following are in "nodes"
                .replace("\"class\":null,", "")
                .replace(",\"updated\":null,\"metadata\":null,\"metrics\":null", "")
                .replace("\"display_name\":null,", "")
                // the remaining are in "connections"
                .replace("\"renderer\":null,", "")
                .replace(",\"name\":null", "")
                .replace("_node_name", "")
                .replace("display_name", "displayName");
        */
    }


    /*
     * This function feeds data over a web socket to update the "live"
     * timeseries chart showing the current, volumetric ingestion rate

    @Scheduled(fixedRate=3000)
    public void updateChart() throws NumberFormatException, Exception {
        // On start-up, wait one iteration before publishing the count
        //LOG.debug("In updateChart()");
        if (lastTimestamp == -1L) {
            lastTimestamp = System.currentTimeMillis() / 1000L;
            return;
        } else {
            // TODO CHANGEME from hardcoded when moving to streaming
            Integer count = applogRepository.getNewRegionEntryCount(1493062152367L); // lastTimestamp
            webSocketMessageTemplate.convertAndSend("/topic/chart", count);
            lastTimestamp = System.currentTimeMillis() / 1000L;
        }
    }

    */

}
