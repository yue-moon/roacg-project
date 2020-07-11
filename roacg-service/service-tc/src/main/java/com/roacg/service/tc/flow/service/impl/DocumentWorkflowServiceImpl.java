package com.roacg.service.tc.flow.service.impl;

import com.roacg.service.tc.flow.model.dto.ContributorDTO;
import com.roacg.service.tc.flow.model.enums.WorkflowDeliveryTagEnum;
import com.roacg.service.tc.flow.model.po.DocumentWorkflowPO;
import com.roacg.service.tc.flow.repository.DocumentVersionRepository;
import com.roacg.service.tc.flow.repository.DocumentWorkflowRepository;
import com.roacg.service.tc.flow.service.DocumentWorkflowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentWorkflowServiceImpl implements DocumentWorkflowService {

    @Resource
    private DocumentWorkflowRepository workflowRepository;

    @Resource
    private DocumentVersionRepository versionRepository;


    @Override
    @Transactional
    public List<DocumentWorkflowPO> generateDocumentWorkflow(Long documentId, List<ContributorDTO> contributors) {

        List<DocumentWorkflowPO> workflow = contributors.stream().map(contributor -> {
            DocumentWorkflowPO workflowNode = new DocumentWorkflowPO();
            workflowNode.setContributorId(contributor.getContributorId());
            workflowNode.setResponsibilities(contributor.getResponsibilities());
            workflowNode.setDeliveryTag(WorkflowDeliveryTagEnum.NOT_STARTED);
            workflowNode.setDocumentId(documentId);
            return workflowNode;
        }).collect(Collectors.toList());

        workflowRepository.saveAll(workflow);

        //双指针设置前后节点
        for (int length = workflow.size(), i = 0, j = length - 1; i < length; i++, j--) {

            DocumentWorkflowPO inode = workflow.get(i), jnode = workflow.get(j);
            if (i > 0) {
                inode.setPrevNode(workflow.get(i - 1).getId());
            }

            if (j < (length - 1)) {
                jnode.setNextNode(workflow.get(j + 1).getId());
            }
        }

        workflowRepository.saveAll(workflow);
        return workflow;
    }
}
