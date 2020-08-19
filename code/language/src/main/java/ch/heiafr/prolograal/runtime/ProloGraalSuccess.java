package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.nodes.ProloGraalProofTreeNode;
import ch.heiafr.prolograal.nodes.ProloGraalResolverNode;
import com.oracle.truffle.api.interop.*;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.Map;

/**
 * Class representing a success.
 * It is capable of holding the state of the variables that lead to the success.
 * @see ProloGraalResolverNode
 * @see ProloGraalProofTreeNode
 * @author Martin Spoto
 */
@ExportLibrary(InteropLibrary.class)
public class ProloGraalSuccess extends ProloGraalBoolean {

   private Map<ProloGraalVariable, ProloGraalVariable> variables;

   public ProloGraalSuccess() {

   }

   public ProloGraalSuccess(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      this.variables = variables;
   }

   public Map<ProloGraalVariable, ProloGraalVariable> getVariables() {
      return this.variables;
   }

   @Override
   public String toString() {
      return "yes";
   }

   @ExportMessage
   public boolean isBoolean(){
      return true;
   }

   @Override
   @ExportMessage
   public boolean asBoolean() {
      return true;
   }

   @ExportMessage
   public boolean hasMembers() {
      return variables.size()>0;
   }

   //TODO: figure out what internal names are
   @ExportMessage
   public Object getMembers(boolean includeInternal) throws UnsupportedMessageException {
      if(hasMembers()){
         String[] namesArray = new String[variables.size()];
         int index = 0;
         for(ProloGraalVariable var: variables.keySet()){
            namesArray[index] = var.toString();
            index++;
         }
         return namesArray;
      }else{
         throw  UnsupportedMessageException.create();
      }
   }

   private ProloGraalVariable getVarByName(String name){
      if(hasMembers()){
         ProloGraalVariable var = null;
         for(ProloGraalVariable curVar: variables.keySet()){
            if(curVar.getName().equals(name)) var = curVar;
         }
         return var;
      }else{
         return null;
      }
   }

   @ExportMessage
   public boolean isMemberReadable(String member)
   {
      return getVarByName(member)!=null?true:false;
   }

   @ExportMessage
   public Object readMember(String member) throws UnsupportedMessageException, UnknownIdentifierException {
      if(isMemberReadable(member)){
         return variables.get(getVarByName(member));
      }else{
         throw UnknownIdentifierException.create(member);
      }
   }

   @ExportMessage
   public boolean isMemberModifiable(String member){
      return getVarByName(member)!=null?true:false;
   }

   @ExportMessage
   public boolean isMemberInsertable(String member){
      return getVarByName(member)==null?true:false;
   }

   @ExportMessage
   public void writeMember(String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
      if(value instanceof ProloGraalVariable){
         variables.put(new ProloGraalVariable(getVariables(), member), (ProloGraalVariable) value);
      }else{
         Object[] memberArray = {value};
         throw UnsupportedTypeException.create(memberArray);
      }
   }

   @ExportMessage
   public boolean isMemberRemovable(String member){
      return getVarByName(member)!=null?true:false;
   }

   @ExportMessage
   public void removeMember(String member) throws UnsupportedMessageException, UnknownIdentifierException {
      if(isMemberRemovable(member)){
         variables.remove(getVarByName(member));
      }else{
         throw UnknownIdentifierException.create(member);
      }
   }

   @ExportMessage
   public boolean isMemberInvocable(String member){
      return false;
   }

   @ExportMessage
   public Object invokeMember(String member, Object[] arguments)
      throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         throw UnsupportedMessageException.create();
   }

   @ExportMessage
   public boolean isMemberInternal(String member) {
      return false;
   }

   @ExportMessage
   public boolean hasMemberReadSideEffects(String member) {
      return false;
   }

   @ExportMessage
   public boolean hasMemberWriteSideEffects(String member) {
      return false;
   }
}
