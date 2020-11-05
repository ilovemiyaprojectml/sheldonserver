package com.fisglobal.waho.services;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.model.User;

@Service
public class ShiftScheduleEmitterService extends EmitterService {

    @Autowired
    private UserService userService;
    
    /**
     * 
     * notifyManager_Undertime()
     * This service sends a notification to the Manager
     * when employee end undertime shift
     * @param currentUserID 
     * 
     */
    public void notifyManager_Undertime(int currentUserID) {
        log.debug("notifyManager_Undertime() - Start");
        
        int managerid = -1;
        User currentUser = null;
        Optional<User> optional = userService.findById(currentUserID);
        if(optional.isPresent()) {
            currentUser = optional.get();
            managerid = currentUser.getUserParentId();
        }
        
        SseEmitter emitterManager = this.getEmittersMap().get(new Long(managerid));
        if (emitterManager != null) {
            try {
                HashMap<String, Object> emitterData = new HashMap<String, Object>();
                emitterData.put("emitterString", "employeeundertime");
                emitterData.put("empName", currentUser.getFirstName() + " " + currentUser.getLastName());
                emitterManager.send(SseEmitter.event().name(String.valueOf(managerid)).data(emitterData));
                emitterManager.complete();
            } catch (IOException e) {
                emitterManager.completeWithError(e);
            } catch (IllegalStateException e) {
                System.out.println("============  IllegalStateException : ShiftScheduleController : endShift()  ============");
                emitterManager.completeWithError(e);
            } catch (Exception e) {
                log.debug("Error encountered during Manager notification", e);
            }
        }
        
        log.debug("notifyManager_Undertime() - End");
    }
        
}
