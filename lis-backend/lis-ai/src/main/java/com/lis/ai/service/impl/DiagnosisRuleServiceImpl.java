package com.lis.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.ai.dto.DiagnosisRuleDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.entity.AiDiagnosisRuleItemDO;
import com.lis.ai.mapper.AiDiagnosisRuleItemMapper;
import com.lis.ai.mapper.AiDiagnosisRuleMapper;
import com.lis.ai.service.DiagnosisRuleService;
import com.lis.ai.vo.DiagnosisRuleVO;
import com.lis.common.result.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosisRuleServiceImpl implements DiagnosisRuleService {

    private final AiDiagnosisRuleMapper ruleMapper;
    private final AiDiagnosisRuleItemMapper ruleItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRule(DiagnosisRuleDTO dto) {
        AiDiagnosisRuleDO existingRule = ruleMapper.selectOne(
                new LambdaQueryWrapper<AiDiagnosisRuleDO>()
                        .eq(AiDiagnosisRuleDO::getRuleCode, dto.getRuleCode())
        );
        if (existingRule != null) {
            throw new IllegalArgumentException("规则编码已存在: " + dto.getRuleCode());
        }

        AiDiagnosisRuleDO rule = new AiDiagnosisRuleDO();
        BeanUtils.copyProperties(dto, rule);
        ruleMapper.insert(rule);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (DiagnosisRuleDTO.RuleItemDTO itemDTO : dto.getItems()) {
                AiDiagnosisRuleItemDO item = new AiDiagnosisRuleItemDO();
                item.setRuleId(rule.getId());
                item.setTestItemId(itemDTO.getTestItemId());
                item.setItemCode(itemDTO.getItemCode());
                item.setItemName(itemDTO.getItemName());
                item.setConditionType(itemDTO.getConditionType());
                item.setConditionExpr(itemDTO.getConditionExpr());
                item.setWeight(itemDTO.getWeight());
                item.setSort(itemDTO.getSort());
                ruleItemMapper.insert(item);
            }
        }

        log.info("创建诊断规则: ruleCode={}, ruleName={}", dto.getRuleCode(), dto.getRuleName());
        return rule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRule(DiagnosisRuleDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("规则ID不能为空");
        }

        AiDiagnosisRuleDO rule = ruleMapper.selectById(dto.getId());
        if (rule == null) {
            throw new IllegalArgumentException("规则不存在");
        }

        if (!rule.getRuleCode().equals(dto.getRuleCode())) {
            AiDiagnosisRuleDO existingRule = ruleMapper.selectOne(
                    new LambdaQueryWrapper<AiDiagnosisRuleDO>()
                            .eq(AiDiagnosisRuleDO::getRuleCode, dto.getRuleCode())
                            .ne(AiDiagnosisRuleDO::getId, dto.getId())
            );
            if (existingRule != null) {
                throw new IllegalArgumentException("规则编码已存在: " + dto.getRuleCode());
            }
        }

        BeanUtils.copyProperties(dto, rule);
        ruleMapper.updateById(rule);

        ruleItemMapper.delete(new LambdaQueryWrapper<AiDiagnosisRuleItemDO>()
                .eq(AiDiagnosisRuleItemDO::getRuleId, rule.getId()));

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (DiagnosisRuleDTO.RuleItemDTO itemDTO : dto.getItems()) {
                AiDiagnosisRuleItemDO item = new AiDiagnosisRuleItemDO();
                item.setRuleId(rule.getId());
                item.setTestItemId(itemDTO.getTestItemId());
                item.setItemCode(itemDTO.getItemCode());
                item.setItemName(itemDTO.getItemName());
                item.setConditionType(itemDTO.getConditionType());
                item.setConditionExpr(itemDTO.getConditionExpr());
                item.setWeight(itemDTO.getWeight());
                item.setSort(itemDTO.getSort());
                ruleItemMapper.insert(item);
            }
        }

        log.info("更新诊断规则: ruleId={}, ruleCode={}", rule.getId(), rule.getRuleCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(Long id) {
        AiDiagnosisRuleDO rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw new IllegalArgumentException("规则不存在");
        }

        ruleItemMapper.delete(new LambdaQueryWrapper<AiDiagnosisRuleItemDO>()
                .eq(AiDiagnosisRuleItemDO::getRuleId, id));

        ruleMapper.deleteById(id);

        log.info("删除诊断规则: ruleId={}", id);
    }

    @Override
    public DiagnosisRuleVO getRule(Long id) {
        AiDiagnosisRuleDO rule = ruleMapper.selectById(id);
        if (rule == null) {
            return null;
        }
        return convertToVO(rule);
    }

    @Override
    public DiagnosisRuleVO getRuleByCode(String ruleCode) {
        AiDiagnosisRuleDO rule = ruleMapper.selectOne(
                new LambdaQueryWrapper<AiDiagnosisRuleDO>()
                        .eq(AiDiagnosisRuleDO::getRuleCode, ruleCode)
        );
        if (rule == null) {
            return null;
        }
        return convertToVO(rule);
    }

    @Override
    public List<DiagnosisRuleVO> listRulesByCategory(String category) {
        List<AiDiagnosisRuleDO> rules = ruleMapper.selectList(
                new LambdaQueryWrapper<AiDiagnosisRuleDO>()
                        .eq(AiDiagnosisRuleDO::getCategory, category)
                        .eq(AiDiagnosisRuleDO::getIsEnabled, 1)
                        .orderByDesc(AiDiagnosisRuleDO::getPriority)
        );
        return rules.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisRuleVO> listEnabledRules() {
        List<AiDiagnosisRuleDO> rules = ruleMapper.selectList(
                new LambdaQueryWrapper<AiDiagnosisRuleDO>()
                        .eq(AiDiagnosisRuleDO::getIsEnabled, 1)
                        .eq(AiDiagnosisRuleDO::getStatus, 0)
                        .orderByDesc(AiDiagnosisRuleDO::getPriority)
        );
        return rules.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public PageResult<DiagnosisRuleVO> queryRules(String ruleName, String category, Integer isEnabled,
                                                    Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<AiDiagnosisRuleDO> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(ruleName)) {
            wrapper.like(AiDiagnosisRuleDO::getRuleName, ruleName);
        }
        if (StringUtils.hasText(category)) {
            wrapper.eq(AiDiagnosisRuleDO::getCategory, category);
        }
        if (isEnabled != null) {
            wrapper.eq(AiDiagnosisRuleDO::getIsEnabled, isEnabled);
        }

        wrapper.orderByDesc(AiDiagnosisRuleDO::getPriority);

        Page<AiDiagnosisRuleDO> page = new Page<>(pageNum, pageSize);
        Page<AiDiagnosisRuleDO> resultPage = ruleMapper.selectPage(page, wrapper);

        List<DiagnosisRuleVO> voList = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(resultPage.getTotal(), pageNum, pageSize, voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableRule(Long id) {
        AiDiagnosisRuleDO rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw new IllegalArgumentException("规则不存在");
        }
        rule.setIsEnabled(1);
        ruleMapper.updateById(rule);
        log.info("启用诊断规则: ruleId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableRule(Long id) {
        AiDiagnosisRuleDO rule = ruleMapper.selectById(id);
        if (rule == null) {
            throw new IllegalArgumentException("规则不存在");
        }
        rule.setIsEnabled(0);
        ruleMapper.updateById(rule);
        log.info("停用诊断规则: ruleId={}", id);
    }

    private DiagnosisRuleVO convertToVO(AiDiagnosisRuleDO rule) {
        DiagnosisRuleVO vo = new DiagnosisRuleVO();
        BeanUtils.copyProperties(rule, vo);

        List<AiDiagnosisRuleItemDO> items = ruleItemMapper.selectList(
                new LambdaQueryWrapper<AiDiagnosisRuleItemDO>()
                        .eq(AiDiagnosisRuleItemDO::getRuleId, rule.getId())
                        .orderByAsc(AiDiagnosisRuleItemDO::getSort)
        );

        if (!items.isEmpty()) {
            List<DiagnosisRuleVO.RuleItemVO> itemVOs = items.stream()
                    .map(item -> {
                        DiagnosisRuleVO.RuleItemVO itemVO = new DiagnosisRuleVO.RuleItemVO();
                        BeanUtils.copyProperties(item, itemVO);
                        return itemVO;
                    })
                    .collect(Collectors.toList());
            vo.setItems(itemVOs);
        }

        return vo;
    }
}
