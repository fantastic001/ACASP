package agents.acasp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;

import javax.jms.Message;

import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.message.MessageManager;

public class Master implements Agent {

    private MessageManager messageManager;
    private String persistencyPath;
    private HashSet<String> contents;

    @Override
    public AID getId() {
        return new AID("", "localhost", new AgentType("master", "stefan.agents.acasp"));
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        if (msg.getSender().getType().getFullName().equals("stefan.agents.acasp.processor")) {
            String text = msg.getContent();
            if (!contents.contains(text)) {
                contents.add(text);
                FileWriter writer;
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] encodedhash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
                    String path = this.bytesToHex(encodedhash);
                    System.out.println("Saving data to " + path);
                    writer = new FileWriter(persistencyPath + "/" + path);
                    writer.write(text);
                    writer.close();
                } catch (IOException | NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void handleStart(Map<String, String[]> params) {
        persistencyPath = params.getOrDefault("persistencyPath", new String[] {"/opt/data/"})[0];

    }

    @Override
    public void handleStop() {

    }

    @Override
    public void init(MessageManager messageManager) {
        this.messageManager = messageManager;
        contents = new HashSet<>();

    }

    @Override
    public void deinit() {

    }
    
}